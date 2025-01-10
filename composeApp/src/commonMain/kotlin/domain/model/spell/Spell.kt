package domain.model.spell

import domain.model.Ability
import domain.model.DamageType
import domain.model.Level
import kotlinx.serialization.Serializable

/**
 * Represents a spell.
 */
data class Spell(
    val key: String,
    val name: String,
    val level: Level,
    var isFavorite: Boolean = false,
    val description: String,
    val higherLevel: String? = null,
    val school: MagicSchool,
    val range: String,
    val verbal: Boolean = false,
    val somatic: Boolean = false,
    val material: Boolean = false,
    val cost: String? = null,
    val ritual: Boolean,
    val duration: String,
    val concentration: Boolean,
    val castingTime: String,
    val castingOptions: List<SpellOption> = emptyList(),
    val savingThrowAbility: Ability? = null,
    val attackRoll: Boolean = false,
    val damageType: List<DamageType> = emptyList(),
    val targetCount: Int? = null,
) {
    @Serializable
    data class SpellOption(
        val level: Level,
        val targetCount: Int? = null,
        val damageRoll: String? = null,
        val duration: String? = null
    )
}