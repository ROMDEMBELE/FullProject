package domain.model

import androidx.compose.ui.graphics.Color
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.ability_cha
import org.dembeyo.shared.resources.ability_con
import org.dembeyo.shared.resources.ability_dex
import org.dembeyo.shared.resources.ability_int
import org.dembeyo.shared.resources.ability_str
import org.dembeyo.shared.resources.ability_wis
import org.jetbrains.compose.resources.StringResource


enum class Ability(val id: String, val stringRes: StringResource) {
    CHA("cha", Res.string.ability_cha),
    CON("con", Res.string.ability_con),
    DEX("dex", Res.string.ability_dex),
    INT("int", Res.string.ability_int),
    STR("str", Res.string.ability_str),
    WIS("wis", Res.string.ability_wis);

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
            in 22..23 -> 6
            in 24..25 -> 7
            in 26..27 -> 8
            in 28..29 -> 9
            in 30..100 -> 10
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


