package domain.model.spell

import domain.model.DamageType
import domain.model.Ability
import domain.model.Level

data class Spell(
    val slug: String,
    val name: String,
    val level: Level,
    val description: String,
    val range: String,
    val verbal: Boolean = false,
    val somatic: Boolean = false,
    val material: Boolean = false,
    val cost: String? = null,
    val ritual: Boolean,
    val duration: String,
    val concentration: Boolean,
    val castingTime: String,
    val attackType: String? = null,
    val castingOptions: List<SpellOption> = emptyList(),
    val savingThrowAbility: Ability? = null,
    val attackRoll: Boolean = false,
    val school: MagicSchool,
    var isFavorite: Boolean = false
) {
    data class SpellOption(
        val level: Level,
        val damageTypes: List<DamageType> = emptyList(),
        val damageRoll: String? = null,
        val duration: String? = null
    )
}