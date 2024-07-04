package ui.magicItem.list

import androidx.compose.ui.text.input.TextFieldValue
import domain.model.magicItem.MagicItem
import domain.model.magicItem.Rarity

data class MagicItemListUiState(
    val magicItemsList: List<MagicItem> = emptyList(),
    val rarityFilter: List<Rarity> = Rarity.entries.toList(),
    var textField: TextFieldValue = TextFieldValue(),
    val error: String? = null,
    val isReady: Boolean = false
) {

    val favoriteItemsByRarity: Map<Rarity, List<MagicItem>>
        get() = magicItemsList.filter { it.isFavorite }
            .sortedBy { it.rarity }
            .groupBy { it.rarity }

    val filteredMagicItemsByRarity: Map<Rarity, List<MagicItem>>
        get() = magicItemsList
            .filter { it.name.contains(textField.text, true) && it.rarity in rarityFilter }
            .sortedBy { it.rarity }
            .groupBy { it.rarity }

    val hasError get() = error != null

    val favoriteCounter get() = favoriteItemsByRarity.values.sumOf { it.size }
}