package ui.spell.details

import domain.model.Ability
import domain.model.DamageType
import domain.model.Level
import domain.model.spell.MagicSchool
import domain.model.spell.Spell.SpellOption

/**
 * Data class representing the state of the spell details screen.
 */
data class SpellDetailsUiState(
    val isReady: Boolean = false,
    val key: String? = null,
    val name: String? = null,
    val level: Level = Level.LEVEL_0,
    val description: String? = null,
    val higherLevelDescription: String? = null,
    val school: MagicSchool = MagicSchool.ILLUSION,
    val isFavorite: Boolean = false,
    val isAttackRoll: Boolean = false,
    val isSavingThrow: Boolean = false,
    val savingThrowAbility: Ability? = null,
    val isConcentration: Boolean = false,
    val isRitual: Boolean = false,
    val range: String? = null,
    val verbal: Boolean = false,
    val somatic: Boolean = false,
    val material: Boolean = false,
    val cost: String? = null,
    val duration: String? = null,
    val castingTime: String? = null,
    val damageType: List<DamageType> = emptyList(),
    val castingOptions: List<SpellOption> = emptyList(),
)