package ui.monster.details

import domain.model.monster.Monster

data class MonsterDetailsUiState(
    val isReady: Boolean = false,
    val monster: Monster? = null,
    val error: String? = null
)