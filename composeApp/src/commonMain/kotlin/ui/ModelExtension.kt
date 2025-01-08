package ui

import androidx.compose.ui.graphics.Color
import domain.model.Alignment
import domain.model.CreatureSize
import domain.model.CreatureType
import domain.model.Level
import domain.model.monster.Challenge
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.aberration
import org.dembeyo.shared.resources.any_alignment
import org.dembeyo.shared.resources.any_evil_alignment
import org.dembeyo.shared.resources.beast
import org.dembeyo.shared.resources.celestial
import org.dembeyo.shared.resources.chaotic_evil
import org.dembeyo.shared.resources.chaotic_good
import org.dembeyo.shared.resources.chaotic_neutral
import org.dembeyo.shared.resources.construct
import org.dembeyo.shared.resources.dragon
import org.dembeyo.shared.resources.elemental
import org.dembeyo.shared.resources.fey
import org.dembeyo.shared.resources.fiend
import org.dembeyo.shared.resources.gargantuan
import org.dembeyo.shared.resources.giant
import org.dembeyo.shared.resources.huge
import org.dembeyo.shared.resources.humanoid
import org.dembeyo.shared.resources.large
import org.dembeyo.shared.resources.lawful_evil
import org.dembeyo.shared.resources.lawful_good
import org.dembeyo.shared.resources.lawful_neutral
import org.dembeyo.shared.resources.medium
import org.dembeyo.shared.resources.monstrosity
import org.dembeyo.shared.resources.neutral
import org.dembeyo.shared.resources.neutral_evil
import org.dembeyo.shared.resources.neutral_good
import org.dembeyo.shared.resources.ooze
import org.dembeyo.shared.resources.plant
import org.dembeyo.shared.resources.small
import org.dembeyo.shared.resources.tiny
import org.dembeyo.shared.resources.unaligned
import org.dembeyo.shared.resources.undead
import org.jetbrains.compose.resources.StringResource


fun Alignment.stringRes(): StringResource {
    return when (this) {
        Alignment.LawfulGood -> Res.string.lawful_good
        Alignment.NeutralGood -> Res.string.neutral_good
        Alignment.ChaoticGood -> Res.string.chaotic_good
        Alignment.LawfulNeutral -> Res.string.lawful_neutral
        Alignment.Neutral -> Res.string.neutral
        Alignment.ChaoticNeutral -> Res.string.chaotic_neutral
        Alignment.LawfulEvil -> Res.string.lawful_evil
        Alignment.NeutralEvil -> Res.string.neutral_evil
        Alignment.ChaoticEvil -> Res.string.chaotic_evil
        Alignment.Unaligned -> Res.string.unaligned
        Alignment.AnyAlignment -> Res.string.any_alignment
        Alignment.AnyEvilAlignment -> Res.string.any_evil_alignment
    }
}

fun CreatureSize.stringRes(): StringResource {
    return when (this) {
        CreatureSize.Tiny -> Res.string.tiny
        CreatureSize.Small -> Res.string.small
        CreatureSize.Medium -> Res.string.medium
        CreatureSize.Large -> Res.string.large
        CreatureSize.Huge -> Res.string.huge
        CreatureSize.Gargantuan -> Res.string.gargantuan
    }
}

fun CreatureType.stringRes(): StringResource {
    return when (this) {
        CreatureType.ABERRATION -> Res.string.aberration
        CreatureType.BEAST -> Res.string.beast
        CreatureType.CELESTIAL -> Res.string.celestial
        CreatureType.CONSTRUCT -> Res.string.construct
        CreatureType.DRAGON -> Res.string.dragon
        CreatureType.ELEMENTAL -> Res.string.elemental
        CreatureType.FEY -> Res.string.fey
        CreatureType.FIEND -> Res.string.fiend
        CreatureType.GIANT -> Res.string.giant
        CreatureType.HUMANOID -> Res.string.humanoid
        CreatureType.MONSTROSITY -> Res.string.monstrosity
        CreatureType.UNDEAD -> Res.string.undead
        CreatureType.OOZE -> Res.string.ooze
        CreatureType.PLANT -> Res.string.plant

    }
}

fun Challenge.color(): Color {
    val minChallenge = 0.0
    val maxChallenge = 30.0

    // Normalize the challenge rating to a value between 0 and 1
    val normalized =
        ((this.rating - minChallenge) / (maxChallenge - minChallenge)).coerceIn(0.0, 1.0)

    // Interpolate colors from green to red
    val red = (255 * normalized).toInt()
    val green = (255 * (1 - normalized)).toInt()
    val blue = 0

    return Color(red, green, blue)
}

fun Level.color(): Color {
    // Define the color for the minimum level (pale green) and maximum level (dark ominous red)
    val startColor = Color(0xFF98FB98) // Pale Green
    val endColor = Color(0xFF8B0000)   // Dark Ominous Red

    // Calculate the total number of levels
    val totalLevels = Level.entries.size - 1

    // Get the current level's index (0-based)
    val currentIndex = this.ordinal

    // Interpolate the RGB components
    val red =
        startColor.red + (endColor.red - startColor.red) * (currentIndex.toFloat() / totalLevels)
    val green =
        startColor.green + (endColor.green - startColor.green) * (currentIndex.toFloat() / totalLevels)
    val blue =
        startColor.blue + (endColor.blue - startColor.blue) * (currentIndex.toFloat() / totalLevels)

    return Color(red, green, blue)
}