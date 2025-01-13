package ui.monster.search

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import ui.composable.darkPrimary
import ui.composable.propertyText
import ui.monster.search.composable.MonsterList

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
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            stringResource(
                                Res.string.filter_min_challenge_rating,
                                state.minChallenge.rating
                            ),
                            style = propertyText,
                            color = darkPrimary
                        )

                        Text(
                            stringResource(
                                Res.string.filter_challenge_rating,
                                state.searchResultCount
                            ),
                            style = SmallBoldSecondary,
                            color = darkPrimary,
                        )

                        Text(
                            stringResource(
                                Res.string.filter_max_challenge_rating,
                                state.maxChallenge.rating
                            ),
                            style = propertyText,
                            color = darkPrimary
                        )

                    }

                    RangeSlider(
                        value = state.filterChallengeRange,
                        onValueChange = { viewModel.setChallengeRange(it) },
                        valueRange = state.challengeRange,
                        steps = Challenge.entries.size,
                        modifier = Modifier.fillMaxWidth(),
                        colors = SliderDefaults.colors(
                            thumbColor = darkPrimary,
                            activeTrackColor = darkPrimary,
                            inactiveTrackColor = darkGray
                        )
                    )

                }
            },
        )

        TaperedRule()

        if (state.isLoading) {
            CustomAnimatedPlaceHolder(
                backgroundColor = Color.Transparent,
                contentColor = darkPrimary
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


