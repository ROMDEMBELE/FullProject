package ui.spell.search

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
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
import domain.model.Level
import ui.composable.CustomAnimatedPlaceHolder
import ui.composable.MediumBoldDarkBlue
import ui.composable.SearchMenu
import ui.composable.TaperedRule
import ui.composable.darkBlue
import ui.composable.primary
import ui.composable.secondary
import ui.spell.search.composable.SpellList

@Composable
fun SearchSpellScreen(
    navHostController: NavHostController,
    viewModel: SearchSpellViewModel,
) {

    val uiState by viewModel.uiState.collectAsState()
    var showFavorite by rememberSaveable { mutableStateOf(false) }

    val onFavoritesClick: (SearchSpellItem) -> Unit = {
        if (it.isFavorite) {
            viewModel.removeFavorite(it.key)
        } else {
            viewModel.addFavorite(it.key)
        }
    }

    val onSpellClick: (SearchSpellItem) -> Unit = {
        navHostController.navigate( "spell/${it.key}")
    }

    Column(
        Modifier.fillMaxSize().background(secondary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchMenu(
            searchTextPlaceholder = "Search by name",
            searchTextFieldValue = uiState.textField,
            onTextChange = { viewModel.filterByText(it) },
            favoriteCounter = uiState.favoritesCounter,
            filterCounter = uiState.filterCounter,
            favoriteEnabled = showFavorite,
            onFavoritesClick = { showFavorite = !showFavorite },
        ) {
            LazyVerticalGrid(
                modifier = Modifier.padding(8.dp),
                columns = GridCells.Fixed(2),
            ) {
                items(Level.entries.subList(0, 10)) { level ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.height(30.dp)
                    ) {
                        Checkbox(
                            colors = CheckboxDefaults.colors(darkBlue),
                            checked = uiState.filterByLevel[level] ?: false,
                            onCheckedChange = { checked ->
                                viewModel.filterByLevel(level, checked)
                            })
                        Text(
                            text = "Level ${level.level}",
                            modifier = Modifier.weight(1f),
                            style = MediumBoldDarkBlue.copy(
                                textAlign = TextAlign.Start
                            )
                        )
                    }
                }
            }
        }
        TaperedRule()

        AnimatedContent(uiState) { state ->
            if (state.isLoading) {
                CustomAnimatedPlaceHolder(
                    backgroundColor = Color.Transparent,
                    contentColor = primary
                )
            } else if (uiState.showFavorites) {
                SpellList(uiState.favoriteByLevel, onFavoritesClick, onSpellClick)
            } else {
                SpellList(uiState.filteredSpellsByLevel, onFavoritesClick, onSpellClick)
            }
        }
    }
}


