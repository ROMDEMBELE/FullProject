package domain.model.spell

import domain.model.DamageType
import domain.model.Level


open class Spell(
    open val index: String,
    open val name: String,
    open val level: Level,
    open var isFavorite: Boolean = false,
) {

    data class SpellDetails(
        override val index: String,
        override val name: String,
        override val level: Level,
        override var isFavorite: Boolean,
        val text: String,
        val range: String,
        val components: String,
        val material: String? = null,
        val ritual: Boolean,
        val duration: String,
        val concentration: Boolean,
        val castingTime: String,
        val attackType: String? = null,
        val areaOfEffect: String? = null,
        val damageByLevel: Map<Level, SpellDamage> = emptyMap(),
        val savingThrow: String? = null,
        val school: MagicSchool,
    ) : Spell(index, name, level, isFavorite)

    data class SpellDamage(
        val type: DamageType,
        val dice: String,
    )
}