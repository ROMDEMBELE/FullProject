package ui.magicItem.search

import androidx.compose.ui.text.input.TextFieldValue
import domain.model.magicItem.ItemRarity

data class SearchMagicItemUiState(
    val favorites: List<SearchMagicItemItem> = emptyList(),
    val searchResult: List<SearchMagicItemItem> = emptyList(),
    val rarityFilter: ItemRarity? = null,
    var searchTextFieldValue: TextFieldValue = TextFieldValue(),
    val isLoading: Boolean = false
) {

    val favoriteItemsByRarity: Map<ItemRarity, List<SearchMagicItemItem>>
        get() = favorites
            .sortedBy { it.rarity }
            .groupBy { it.rarity }

    val searchResultByRarity: Map<ItemRarity, List<SearchMagicItemItem>>
        get() = searchResult
            .sortedBy { it.rarity }
            .groupBy { it.rarity }

    val favoriteCounter get() = favorites.size

    val searchCounter get() = searchResult.size

    val rarityRange: List<ItemRarity?>
        get() = listOf(
            null,
            ItemRarity.COMMON,
            ItemRarity.UNCOMMON,
            ItemRarity.RARE,
            ItemRarity.VERY_RARE,
            ItemRarity.LEGENDARY,
            ItemRarity.ARTIFACT
        )
}