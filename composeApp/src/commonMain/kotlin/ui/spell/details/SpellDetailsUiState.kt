package ui.spell.details

import domain.model.Ability
import domain.model.Level
import domain.model.spell.MagicSchool
import domain.model.spell.Spell.SpellOption

data class SpellDetailsUiState(
    val isReady: Boolean = false,
    val slug: String? = null,
    val name: String? = null,
    val level: Level = Level.LEVEL_0,
    val description: String? = null,
    val range: String? = null,
    val verbal: Boolean = false,
    val somatic: Boolean = false,
    val material: Boolean = false,
    val cost: String? = null,
    val ritual: Boolean = false,
    val duration: String? = null,
    val concentration: Boolean = false,
    val castingTime: String? = null,
    val attackType: String? = null,
    val castingOptions: List<SpellOption> = emptyList(),
    val savingThrowAbility: Ability? = null,
    val attackRoll: Boolean = false,
    val school: MagicSchool = MagicSchool.ILLUSION,
    var isFavorite: Boolean = false,
    val error: String? = null
)