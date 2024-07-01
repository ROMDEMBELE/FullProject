package domain.model

data class SavingThrow(
    val value: Int? = null,
    val ability: Ability,
    val success: String? = null
) {
    override fun toString(): String = buildString {
        append("${ability.fullName} saving throw")
        if (value != null)
            append(" DD $value")
        if (success != null)
            append(" for $success")
    }
}