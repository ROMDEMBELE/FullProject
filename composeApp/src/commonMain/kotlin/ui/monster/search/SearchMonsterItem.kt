package ui.monster.search

import domain.model.monster.Challenge

/**
 * Item of the monster list to display in the search screen.
 */
data class SearchMonsterItem(
    val slug: String,
    val name: String,
    val isFavorite: Boolean,
    val challenge: Challenge,
)
