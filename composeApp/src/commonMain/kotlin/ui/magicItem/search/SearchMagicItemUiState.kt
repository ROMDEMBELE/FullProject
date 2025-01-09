package ui.magicItem.search

import androidx.compose.ui.text.input.TextFieldValue
import domain.model.magicItem.Rarity

data class SearchMagicItemUiState(
    val favoriteItems: List<SearchMagicItemItem> = emptyList(),
    val magicItemsList: List<SearchMagicItemItem> = emptyList(),
    val rarityFilter: List<Rarity> = Rarity.entries.toList(),
    var textField: TextFieldValue = TextFieldValue(),
    val error: String? = null,
    val isReady: Boolean = false
) {

    val favoriteItemsByRarity: Map<Rarity, List<SearchMagicItemItem>>
        get() = favoriteItems
            .sortedBy { it.rarity }
            .groupBy { it.rarity }

    val filteredMagicItemsByRarity: Map<Rarity, List<SearchMagicItemItem>>
        get() = magicItemsList
            .filter { it.name.contains(textField.text, true) && it.rarity in rarityFilter }
            .sortedBy { it.rarity }
            .groupBy { it.rarity }

    val hasError get() = error != null

    val favoriteCounter get() = favoriteItems.size
}