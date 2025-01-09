package ui.spell.search

import domain.model.Level

data class SearchSpellItem(
    val key: String,
    val name: String,
    val level: Level,
    val isFavorite: Boolean,
)