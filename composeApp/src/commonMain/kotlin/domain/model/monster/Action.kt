package domain.model.monster

import domain.model.DamageType


 class Action(
     val name: String,
     val desc: String,
     val usage: String,
     val legendaryCost: Int? = null,
     val attacks: List<Attack> = emptyList()
) {

    data class Attack(
        val type: String,
        val damageDice: String,
        val damageType: DamageType,
        val attackBonus: Int,
        val reach: Double? = null,
        val range: Double? = null,
        val longRange: Double? = null,
    )
}