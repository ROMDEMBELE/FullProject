package domain

import androidx.compose.ui.graphics.Color


enum class Ability(val id: String, val fullName: String) {
    CHA("cha", "Charisma"),
    CON("con", "Constitution"),
    DEX("dex", "Dexterity"),
    INT("int", "Intelligence"),
    STR("str", "Strength"),
    WIS("wis", "Wisdom");

    companion object {
        fun Int.getAbilityBonus(): Int = when (this) {
            in 0..1 -> -5
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
            in 22..30 -> 6
            else -> throw IllegalArgumentException("Invalid characteristic value")
        }

        fun Int.getAbilityBonusColor(): Color = when (this) {
            in 0..1 -> Color(0xFFFFC0C0) // Pale Red
            in 2..3 -> Color(0xFFFFD0A0) // Pale Orange-Red
            in 4..5 -> Color(0xFFFFE0A0) // Pale Orange
            in 6..7 -> Color(0xFFFFF0A0) // Pale Yellow-Orange
            in 8..9 -> Color(0xFFFFFFA0) // Pale Yellow
            in 10..11 -> Color(0xFFDFFF80) // Pale Yellow-Green
            in 12..13 -> Color(0xFFBFFF80) // Pale Light Green
            in 14..15 -> Color(0xFF80FF80) // Pale Greenish
            in 16..17 -> Color(0xFF80FF80) // Pale Green
            in 18..19 -> Color(0xFF80FFB0) // Pale Light Green
            in 20..21 -> Color(0xFF80FFD0) // Pale Turquoise
            in 22..30 -> Color(0xFF80FFE0) // Pale Light Blue
            else -> throw IllegalArgumentException("Invalid characteristic value")
        }
    }
}


