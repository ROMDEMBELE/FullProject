package ui.spell.search

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
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
import org.dembeyo.shared.resources.filter_level
import org.dembeyo.shared.resources.filter_max_level
import org.dembeyo.shared.resources.filter_min_level
import org.dembeyo.shared.resources.search_spell_text_field_hint
import org.jetbrains.compose.resources.stringResource
import ui.composable.CustomAnimatedPlaceHolder
import ui.composable.SearchMenu
import ui.composable.SmallBoldSecondary
import ui.composable.TaperedRule
import ui.composable.darkGray
import ui.composable.darkPrimary
import ui.composable.propertyText
import ui.composable.secondary
import ui.spell.search.composable.SpellList

@Composable
fun SearchSpellScreen(
    navHostController: NavHostController,
    viewModel: SearchSpellViewModel,
) {

    val uiState by viewModel.state.collectAsState()
    var showFavorites by rememberSaveable { mutableStateOf(false) }

    val onFavoritesClick: (SearchSpellItem) -> Unit = {
        if (it.isFavorite) {
            viewModel.removeFavorite(it.key)
        } else {
            viewModel.addFavorite(it.key)
        }
    }

    val onSpellClick: (SearchSpellItem) -> Unit = {
        navHostController.navigate("spell/${it.key}")
    }

    Column(
        Modifier.fillMaxSize().background(secondary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchMenu(
            searchTextPlaceholder = stringResource(Res.string.search_spell_text_field_hint),
            searchTextFieldValue = uiState.searchTextField,
            onTextChange = { viewModel.filterByText(it) },
            favoriteCounter = uiState.favoritesCounter,
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
                            stringResource(Res.string.filter_min_level, uiState.minLevel.level),
                            style = propertyText,
                            color = darkPrimary
                        )

                        Text(
                            stringResource(Res.string.filter_level, uiState.resultCounter),
                            style = SmallBoldSecondary,
                            color = darkPrimary,
                        )

                        Text(
                            stringResource(Res.string.filter_max_level, uiState.maxLevel.level),
                            style = propertyText,
                            color = darkPrimary
                        )

                    }

                    RangeSlider(
                        value = uiState.filterLevelRange,
                        onValueChange = { viewModel.setLevelRange(it) },
                        valueRange = uiState.levelRange,
                        steps = Challenge.entries.size,
                        modifier = Modifier.fillMaxWidth(),
                        colors = SliderDefaults.colors(
                            thumbColor = darkPrimary,
                            activeTrackColor = darkPrimary,
                            inactiveTrackColor = darkGray
                        )
                    )

                }
            }
        )
        TaperedRule()

        if (uiState.isLoading) {
            CustomAnimatedPlaceHolder(
                backgroundColor = Color.Transparent,
                contentColor = darkPrimary
            )
        } else
            AnimatedContent(showFavorites) { favorite ->
                if (favorite) {
                    SpellList(uiState.favoriteByLevel, onFavoritesClick, onSpellClick)
                } else {
                    SpellList(uiState.spellByLevel, onFavoritesClick, onSpellClick)
                }
            }
    }
}


