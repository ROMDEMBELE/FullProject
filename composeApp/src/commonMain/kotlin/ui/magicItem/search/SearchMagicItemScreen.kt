package ui.magicItem.search

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.filter_rarity
import org.dembeyo.shared.resources.search_magic_item_text_field_hint
import org.jetbrains.compose.resources.stringResource
import ui.composable.CustomAnimatedPlaceHolder
import ui.composable.DropDownTextField
import ui.composable.SearchMenu
import ui.composable.TaperedRule
import ui.composable.darkPrimary
import ui.composable.secondary
import ui.stringRes


@Composable
fun SearchMagicItemScreen(
    navHostController: NavHostController,
    viewModel: SearchMagicItemViewModel,
) {

    val state by viewModel.state.collectAsState()
    var showFavorites by rememberSaveable { mutableStateOf(false) }

    val onFavoriteClick: (SearchMagicItemItem) -> Unit = {
        if (it.isFavorite) {
            viewModel.removeFromFavorite(it.key)
        } else {
            viewModel.addToFavorite(it.key)
        }
    }

    val onItemClick: (SearchMagicItemItem) -> Unit = {
        navHostController.navigate("magic_item/${it.key}")
    }

    Column(
        Modifier.fillMaxSize().background(secondary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchMenu(searchTextPlaceholder = stringResource(Res.string.search_magic_item_text_field_hint),
            searchTextFieldValue = state.searchTextFieldValue,
            onTextChange = { viewModel.onSearchTextFieldChange(it) },
            favoriteCounter = state.favoriteCounter,
            favoriteEnabled = showFavorites,
            onFavoritesClick = {
                showFavorites = !showFavorites
                viewModel.cancelSearch()
            },
            filterContent = {
                Column(modifier = Modifier.padding(16.dp)) {
                    DropDownTextField(
                        selectedValue = state.rarityFilter,
                        display = { this?.let { stringResource(stringRes()) } ?: "---" },
                        label = stringResource(Res.string.filter_rarity, state.searchCounter),
                        values = state.rarityRange,
                        onSelected = viewModel::onRarityFilterChange
                    )
                }
            })

        TaperedRule()

        if (state.isLoading) {
            CustomAnimatedPlaceHolder(
                backgroundColor = Color.Transparent, contentColor = darkPrimary
            )
        } else {
            AnimatedContent(showFavorites) { favorite ->
                if (favorite) {
                    MagicItemList(
                        magicItemsByRarity = state.favoriteItemsByRarity,
                        onFavoriteClick = onFavoriteClick,
                        onItemClick = onItemClick
                    )
                } else {
                    MagicItemList(
                        magicItemsByRarity = state.searchResultByRarity,
                        onFavoriteClick = onFavoriteClick,
                        onItemClick = onItemClick
                    )
                }
            }
        }
    }
}