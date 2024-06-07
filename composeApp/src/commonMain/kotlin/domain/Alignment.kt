package domain

enum class Alignment(val description: String) {
    LAWFUL_GOOD("Lawful Good"),
    NEUTRAL_GOOD("Neutral Good"),
    CHAOTIC_GOOD("Chaotic Good"),
    LAWFUL_NEUTRAL("Lawful Neutral"),
    TRUE_NEUTRAL("True Neutral"),
    CHAOTIC_NEUTRAL("Chaotic Neutral"),
    LAWFUL_EVIL("Lawful Evil"),
    NEUTRAL_EVIL("Neutral Evil"),
    CHAOTIC_EVIL("Chaotic Evil"),
    UNALIGNED("Unaligned");

    companion object {
        fun fromString(description: String): Alignment =
            entries.find { it.description.equals(description, ignoreCase = true) } ?: UNALIGNED
    }
}