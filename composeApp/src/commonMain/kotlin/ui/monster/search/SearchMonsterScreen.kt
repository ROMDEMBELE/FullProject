package ui.monster.search

import AppRoute
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.RangeSlider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import domain.model.monster.Challenge
import ui.composable.CustomAnimatedPlaceHolder
import ui.composable.SearchMenu
import ui.composable.SmallBoldSecondary
import ui.composable.TaperedRule
import ui.composable.darkGray
import ui.composable.darkPrimary
import ui.composable.propertyText
import ui.composable.secondary
import ui.monster.search.composable.MonsterList

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchMonsterScreen(
    navHostController: NavHostController,
    viewModel: SearchMonsterViewModel
) {

    val uiState by viewModel.uiState.collectAsState()

    val onFavoritesClick: (SearchMonsterItem) -> Unit = {
        if (it.isFavorite) {
            viewModel.removeFavorite(it.slug)
        } else {
            viewModel.addFavorite(it.slug)
        }
    }

    val onMonsterClick: (SearchMonsterItem) -> Unit = {
        navHostController.navigate("monster/${it.slug}")
    }

    /*AnimatedVisibility(uiState.hasError) {
        CustomErrorDialog(
            stringResource(Res.string.error_dialog_title),
            uiState.error.orEmpty()
        ) {
            viewModel.acknowledgeError()
        }
    }*/
    Box {
        Column(
            Modifier.fillMaxSize().background(secondary),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchMenu(
                searchTextPlaceholder = "Search by name",
                searchTextFieldValue = uiState.textField,
                onTextChange = { viewModel.filterByText(it) },
                favoriteCounter = uiState.favoritesCount,
                favoriteEnabled = uiState.showFavorites,
                onFavoritesClick = viewModel::toggleFavorites,
                filterContent = {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Min CR ${uiState.minChallenge.rating}",
                                style = propertyText,
                                color = darkPrimary
                            )

                            Text(
                                "Filter by Challenge (${uiState.monsterCount} results)",
                                style = SmallBoldSecondary,
                                color = darkPrimary,
                            )

                            Text(
                                "Max CR ${uiState.maxChallenge.rating}",
                                style = propertyText,
                                color = darkPrimary
                            )

                        }

                        RangeSlider(
                            value = uiState.filterChallengeRange,
                            onValueChange = { viewModel.setChallengeRange(it) },
                            valueRange = uiState.challengeRange,
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

            AnimatedContent(uiState) { state ->
                when {
                    state.isLoading -> {
                        CustomAnimatedPlaceHolder(
                            backgroundColor = Color.Transparent,
                            contentColor = darkPrimary
                        )
                    }

                    state.showFavorites -> {
                        MonsterList(
                            monsterByChallenge = uiState.favoritesByChallenge,
                            onFavoriteClick = onFavoritesClick,
                            onMonsterClick = onMonsterClick
                        )
                    }

                    else -> {
                        MonsterList(
                            monsterByChallenge = uiState.monsterByChallenge,
                            onFavoriteClick = onFavoritesClick,
                            onMonsterClick = onMonsterClick
                        )
                    }
                }
            }
        }
    }
}


