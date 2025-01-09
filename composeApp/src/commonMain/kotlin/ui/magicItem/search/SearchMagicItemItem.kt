package ui.magicItem.search

import domain.model.magicItem.Rarity

data class SearchMagicItemItem(
    val key: String,
    val name: String,
    val rarity: Rarity,
    val isFavorite: Boolean,
)
