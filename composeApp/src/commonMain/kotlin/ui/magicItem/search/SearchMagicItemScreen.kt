package ui.magicItem.search

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import domain.model.magicItem.Rarity
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.error_dialog_title
import org.jetbrains.compose.resources.stringResource
import ui.composable.CustomAnimatedPlaceHolder
import ui.composable.CustomErrorDialog
import ui.composable.MediumBoldDarkBlue
import ui.composable.SearchMenu
import ui.composable.TaperedRule
import ui.composable.darkBlue
import ui.composable.secondary
import ui.stringRes


@Composable
fun SearchMagicItemScreen(
    navHostController: NavHostController,
    viewModel: SearchMagicItemViewModel,
) {

    val uiState by viewModel.uiState.collectAsState()
    var favoriteEnabled by rememberSaveable { mutableStateOf(false) }

    val onFavoriteClick: (SearchMagicItemItem) -> Unit = {

    }

    val onItemClick: (SearchMagicItemItem) -> Unit = {

    }

    AnimatedVisibility(uiState.hasError) {
        CustomErrorDialog(
            stringResource(Res.string.error_dialog_title),
            uiState.error.orEmpty()
        ) {
            viewModel.acknowledgeError()
        }
    }

    CustomAnimatedPlaceHolder()

    AnimatedVisibility(uiState.isReady, enter = fadeIn()) {
        Column(
            Modifier.fillMaxSize().background(secondary),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchMenu(
                searchTextPlaceholder = "Search by name",
                searchTextFieldValue = uiState.textField,
                onTextChange = { viewModel.filterByText(it) },
                favoriteCounter = uiState.favoriteCounter,
                favoriteEnabled = favoriteEnabled,
                onFavoritesClick = { favoriteEnabled = !favoriteEnabled },
                filterContent = {
                    LazyVerticalGrid(
                        modifier = Modifier.padding(8.dp),
                        columns = GridCells.Fixed(2),
                    ) {
                        items(Rarity.entries) { rarity ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.height(30.dp)
                            ) {
                                Checkbox(
                                    colors = CheckboxDefaults.colors(darkBlue),
                                    checked = uiState.rarityFilter.contains(rarity),
                                    onCheckedChange = { checked ->
                                        if (checked) {
                                            viewModel.addRarityToFilter(rarity)
                                        } else {
                                            viewModel.removeRarityFromFilter(rarity)
                                        }
                                    })
                                Text(
                                    text = stringResource(rarity.stringRes()),
                                    modifier = Modifier.weight(1f),
                                    style = MediumBoldDarkBlue.copy(
                                        textAlign = TextAlign.Start
                                    )
                                )
                            }
                        }
                    }
                },
            )

            TaperedRule()

            AnimatedContent(favoriteEnabled) { favorite ->
                if (favorite) {
                    MagicItemList(uiState.favoriteItemsByRarity, onFavoriteClick, onItemClick)
                } else {
                    MagicItemList(uiState.filteredMagicItemsByRarity, onFavoriteClick, onItemClick)
                }
            }
        }
    }
}