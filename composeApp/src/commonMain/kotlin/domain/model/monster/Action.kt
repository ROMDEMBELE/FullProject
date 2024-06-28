package domain.model.monster

import domain.model.DamageType

open class Action(
    override val name: String,
    override val desc: String,
    open val usage: String? = null,
) : MonsterPropertyDescription {

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
        val type: DamageType,
        val dice: String,
        val notes: String? = null,
    )
}