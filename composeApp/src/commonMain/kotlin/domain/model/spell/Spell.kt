package domain.model.spell

import domain.model.DamageType
import domain.model.Level
import domain.model.SavingThrow


open class Spell(
    open val index: String,
    open val name: String,
    open val level: Level,
    open var isFavorite: Boolean = false,
    open val details: Details? = null,
) {

    data class Details(
        val description: List<String>,
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
        val savingThrow: SavingThrow? = null,
        val school: MagicSchool,
    ) {

        data class SpellDamage(
            val type: DamageType? = null,
            val dice: String,
        )
    }

}