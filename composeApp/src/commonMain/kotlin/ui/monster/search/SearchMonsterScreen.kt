package ui.monster.search

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import domain.model.monster.Challenge
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.filter_challenge_rating
import org.dembeyo.shared.resources.filter_max_challenge_rating
import org.dembeyo.shared.resources.filter_min_challenge_rating
import org.dembeyo.shared.resources.search_monster_text_field_hint
import org.jetbrains.compose.resources.stringResource
import ui.composable.CustomAnimatedPlaceHolder
import ui.composable.SearchMenu
import ui.composable.SmallBoldSecondary
import ui.composable.TaperedRule
import ui.composable.darkGray
import ui.composable.primaryDark
import ui.composable.propertyText
import ui.monster.search.composable.MonsterList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchMonsterScreen(
    navHostController: NavHostController,
    viewModel: SearchMonsterViewModel
) {

    val state by viewModel.state.collectAsState()
    var showFavorites by rememberSaveable { mutableStateOf(false) }

    val onFavoritesClick: (SearchMonsterItem) -> Unit = {
        if (it.isFavorite) {
            viewModel.removeFavorite(it.key)
        } else {
            viewModel.addFavorite(it.key)
        }
    }

    val onMonsterClick: (SearchMonsterItem) -> Unit = {
        navHostController.navigate("monster/${it.key}")
    }

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchMenu(
            searchTextPlaceholder = stringResource(Res.string.search_monster_text_field_hint),
            searchTextFieldValue = state.searchTextField,
            onTextChange = viewModel::onSearchTextChange,
            favoriteCounter = state.favoritesCount,
            favoriteEnabled = showFavorites,
            onFavoritesClick = {
                showFavorites = !showFavorites
                viewModel.cancelSearch()
            },
            filterContent = {
                Column(Modifier.padding(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(
                                Res.string.filter_min_challenge_rating,
                                state.minChallenge.rating
                            ),
                            modifier = Modifier.weight(0.25f),
                            style = SmallBoldSecondary,
                            textAlign = TextAlign.Start,
                            color = primaryDark,
                        )

                        Text(
                            text = stringResource(
                                Res.string.filter_challenge_rating,
                                state.searchResultCount
                            ),
                            style = propertyText,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(0.5f).padding(horizontal = 8.dp),
                            color = primaryDark,
                        )

                        Text(
                            text = stringResource(
                                Res.string.filter_max_challenge_rating,
                                state.maxChallenge.rating
                            ),
                            modifier = Modifier.weight(0.25f),
                            style = SmallBoldSecondary,
                            textAlign = TextAlign.End,
                            color = primaryDark
                        )
                    }

                    RangeSlider(
                        value = state.filterChallengeRange,
                        onValueChange = { viewModel.setChallengeRange(it) },
                        valueRange = state.challengeRange,
                        steps = Challenge.entries.size,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                        colors = SliderDefaults.colors(
                            thumbColor = primaryDark,
                            activeTrackColor = primaryDark,
                            inactiveTrackColor = darkGray,
                            activeTickColor = Color.Transparent,
                            inactiveTickColor = Color.Transparent,
                            disabledActiveTickColor = Color.Transparent,
                            disabledInactiveTickColor = Color.Transparent,
                        ),
                        track = { state ->
                            SliderDefaults.Track(
                                rangeSliderState = state,
                                drawStopIndicator = null,
                                colors = SliderDefaults.colors(
                                    activeTrackColor = primaryDark,
                                    inactiveTrackColor = darkGray,
                                    activeTickColor = Color.Transparent,
                                    inactiveTickColor = Color.Transparent
                                ),
                                modifier = Modifier.height(4.dp),
                            )
                        },
                        startThumb = {
                            Box(Modifier.size(15.dp).background(primaryDark, CutCornerShape(15.dp)))
                        },
                        endThumb = {
                            Box(Modifier.size(15.dp).background(primaryDark, CutCornerShape(15.dp)))
                        }
                    )

                }
            },
        )

        TaperedRule()

        if (state.isLoading) {
            CustomAnimatedPlaceHolder(
                backgroundColor = Color.Transparent,
                contentColor = primaryDark
            )
        } else {
            AnimatedContent(showFavorites) { favorite ->
                if (favorite) {
                    MonsterList(
                        monsterByChallenge = state.favoritesByChallenge,
                        onFavoriteClick = onFavoritesClick,
                        onMonsterClick = onMonsterClick
                    )
                } else {
                    MonsterList(
                        monsterByChallenge = state.monsterByChallenge,
                        onFavoriteClick = onFavoritesClick,
                        onMonsterClick = onMonsterClick
                    )
                }
            }
        }
    }
}


