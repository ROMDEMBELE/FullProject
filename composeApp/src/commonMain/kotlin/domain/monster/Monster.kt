package domain.monster

import domain.Ability
import domain.Alignment
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
        val alignment: Alignment,
        val armors: Map<String, Int>,
        val hitPoints: Int,
        val hitPointsRoll: String,
        val movements: Map<CreatureMovement, String>,
        val abilities: Map<Ability, Int>,
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
    )

    data class SpellCastingSpell(
        val level: Level,
        val name: String,
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
        open val spellByLevel: Map<Level, List<SpellCastingSpell>>,
    ) : SavingThrowAbility(name, desc, savingThrow)

    data class SpellCastingAbility(
        override val name: String,
        override val desc: String,
        override val savingThrow: SavingThrow,
        override val components: List<String>,
        override val spellByLevel: Map<Level, List<SpellCastingSpell>>,
        val level: Level,
        val modifier: Int,
        val school: String,
        val slots: Map<Level, Int>,
    ) : InnateSpellCastingAbility(name, desc, savingThrow, components, spellByLevel)

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
