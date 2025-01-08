package ui.monster.list

import domain.model.monster.Challenge

data class MonsterListItem(
    val slug: String,
    val name: String,
    val isFavorite: Boolean,
    val challenge: Challenge,
)
