package ui.magicItem.search

import domain.model.magicItem.ItemRarity

data class SearchMagicItemItem(
    val key: String,
    val name: String,
    val rarity: ItemRarity,
    val isFavorite: Boolean,
)
