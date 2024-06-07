package domain

enum class CreatureSize(val description: String) {
    TINY("Tiny"),
    SMALL("Small"),
    MEDIUM("Medium"),
    LARGE("Large"),
    HUGE("Huge"),
    GARGANTUAN("Gargantuan");

    companion object {
        fun fromString(description: String): CreatureSize {
            return entries.find { it.description.equals(description, ignoreCase = true) }
                ?: throw IllegalArgumentException("Unknown size: $description")
        }
    }
}