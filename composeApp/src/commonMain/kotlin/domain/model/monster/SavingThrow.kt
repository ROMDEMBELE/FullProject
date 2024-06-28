package domain.model.monster

import domain.model.Ability

data class SavingThrow(
    val value: Int,
    val ability: Ability,
    val success: String? = null
) {
    override fun toString(): String = "${ability.fullName} saving throw DD $value for $success"
}