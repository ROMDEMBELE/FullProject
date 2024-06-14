package domain.spell

import domain.Level


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
        val material: String,
        val ritual: Boolean,
        val duration: String,
        val concentration: Boolean,
        val castingTime: String,
        val attackType: String,
        val damageType: String,
        val damageSlot: Map<Level, String>,
        val save: String? = null,
        val school: MagicSchool,
    ) : Spell(index, name, level, isFavorite)
}