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
    }
}

