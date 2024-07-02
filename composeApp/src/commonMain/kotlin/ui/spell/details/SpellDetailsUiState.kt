package ui.spell.details

import domain.model.spell.Spell

data class SpellDetailsUiState(
    val isReady: Boolean = false,
    val spell: Spell? = null,
    val error: String? = null
)