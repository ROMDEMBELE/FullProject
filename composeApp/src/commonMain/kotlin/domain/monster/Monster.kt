package domain.monster

import CreatureAlignment
import domain.Ability
import domain.Level

open class Monster(
    open val index: String,
    open val name: String,
    open val challenge: Challenge,
    open val isFavorite: Boolean = false,
) {
    data class MonsterDetails(
        override val index: String,
        override val name: String,
        override val challenge: Challenge,
        override val isFavorite: Boolean,
        val size: CreatureSize,
        val type: CreatureType,
        val alignment: CreatureAlignment,
        val armorsClass: Map<String, Int>,
        val hitPoints: Int,
        val hitPointsRoll: String,
        val speedByMovements: Map<CreatureMovement, String>,
        val scoreByAbilities: Map<Ability, Int>,
        val savingThrows: List<SavingThrow>,
        val skills: List<String>,
        val damageVulnerabilities: List<String>,
        val damageResistances: List<String>,
        val damageImmunities: List<String>,
        val conditionImmunities: List<String>,
        val senses: Map<CreatureSense, String>,
        val languages: String,
        val xp: Int,
        val specialAbilities: List<SpecialAbility>,
        val actions: List<Action>,
        val legendaryActions: List<Action>,
        val image: String? = null
    ) : Monster(index, name, challenge, isFavorite)


    data class SavingThrow(
        val value: Int,
        val ability: Ability,
        val success: String? = null
    ) {
        override fun toString(): String = "${ability.fullName} saving throw DD $value for $success"
    }

    data class SpellCasting(
        val level: Level,
        val name: String,
        val index: String,
        val notes: String? = null,
        val usage: String? = null,
    )

    interface MonsterPropertyDescription {
        val name: String
        val desc: String
    }

    open class SpecialAbility(
        override val name: String,
        override val desc: String,
    ) : MonsterPropertyDescription

    open class SavingThrowAbility(
        override val name: String,
        override val desc: String,
        open val savingThrow: SavingThrow,
    ) : SpecialAbility(name, desc)

    open class InnateSpellCastingAbility(
        override val name: String,
        override val desc: String,
        override val savingThrow: SavingThrow,
        open val components: List<String>,
        val spellByUsage: Map<String, List<SpellCasting>> = emptyMap()
    ) : SavingThrowAbility(name, desc, savingThrow) {

        open fun buildDescription(name: String): String {
            return buildString {
                append("The $name spell casting ability ${savingThrow.ability.fullName}")
                append(" (spell save DC ${savingThrow.value})")
            }
        }
    }

    data class SpellCastingAbility(
        override val name: String,
        override val desc: String,
        override val savingThrow: SavingThrow,
        override val components: List<String>,
        val spellByLevel: Map<Level, List<SpellCasting>> = emptyMap(),
        val level: Level,
        val ability: Ability,
        val modifier: Int,
        val dc: Int,
        val school: String,
        val slots: Map<Level, Int>,
    ) : InnateSpellCastingAbility(name, desc, savingThrow, components) {

        override fun buildDescription(name: String): String {
            return buildString {
                append("The $name is an ${level.level}th-level spell caster")
                append(" with ${ability.fullName} spell casting ability")
                append(" (spell save DC ${dc}, +${modifier} to hit with spell attacks)")
            }
        }
    }

    open class Action(
        override val name: String,
        override val desc: String,
        open val usage: String? = null,
    ) : MonsterPropertyDescription

    data class AttackAction(
        override val name: String,
        override val desc: String,
        val damage: List<Damage>,
        val attackBonus: Int,
    ) : Action(name, desc)

    data class MultiAttackAction(
        override val name: String,
        override val desc: String,
        val choose: Int,
        val attacks: List<String>
    ) : Action(name, desc)

    data class SavingThrowAction(
        override val name: String,
        override val desc: String,
        override val usage: String? = null,
        val damage: List<Damage>,
        val savingThrow: SavingThrow,
    ) : Action(name, desc)

    data class Damage(
        val type: String,
        val dice: String,
        val notes: String? = null,
    )
}
