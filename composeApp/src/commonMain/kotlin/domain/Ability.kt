package domain

enum class Ability(val id: String, val fullName: String) {
    CHA("cha", "Charisma"),
    CON("con", "Constitution"),
    DEX("dex", "Dexterity"),
    INT("int", "Intelligence"),
    STR("str", "Strength"),
    WIS("wis", "Wisdom");

    companion object {
        fun fromId(id: String): Ability? {
            return entries.find { it.id == id }
        }

        fun Int.getModifier(): Int {
            return when (this) {
                in 2..3 -> -4
                in 4..5 -> -3
                in 6..7 -> -2
                in 8..9 -> -1
                in 10..11 -> 0
                in 12..13 -> 1
                in 14..15 -> 2
                in 16..17 -> 3
                in 18..19 -> 4
                in 20..21 -> 5
                else -> throw IllegalArgumentException("Invalid characteristic value")
            }
        }
    }
}

