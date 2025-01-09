package ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.util.fastJoinToString
import domain.model.Ability
import domain.model.Alignment
import domain.model.Condition
import domain.model.DamageType
import domain.model.Level
import domain.model.magicItem.Rarity
import domain.model.monster.Challenge
import domain.model.monster.CreatureSize
import domain.model.monster.CreatureType
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.aberration
import org.dembeyo.shared.resources.ability_cha
import org.dembeyo.shared.resources.ability_con
import org.dembeyo.shared.resources.ability_dex
import org.dembeyo.shared.resources.ability_int
import org.dembeyo.shared.resources.ability_str
import org.dembeyo.shared.resources.ability_wis
import org.dembeyo.shared.resources.any_alignment
import org.dembeyo.shared.resources.any_chaotic_alignment
import org.dembeyo.shared.resources.any_evil_alignment
import org.dembeyo.shared.resources.any_good_alignment
import org.dembeyo.shared.resources.any_non_good_alignment
import org.dembeyo.shared.resources.any_non_lawful_alignment
import org.dembeyo.shared.resources.artifact
import org.dembeyo.shared.resources.beast
import org.dembeyo.shared.resources.celestial
import org.dembeyo.shared.resources.chaotic_evil
import org.dembeyo.shared.resources.chaotic_good
import org.dembeyo.shared.resources.chaotic_neutral
import org.dembeyo.shared.resources.common
import org.dembeyo.shared.resources.condition_blinded
import org.dembeyo.shared.resources.condition_charmed
import org.dembeyo.shared.resources.condition_deafened
import org.dembeyo.shared.resources.condition_exhaustion
import org.dembeyo.shared.resources.condition_frightened
import org.dembeyo.shared.resources.condition_grappled
import org.dembeyo.shared.resources.condition_incapacitated
import org.dembeyo.shared.resources.condition_invisible
import org.dembeyo.shared.resources.condition_paralyzed
import org.dembeyo.shared.resources.condition_petrified
import org.dembeyo.shared.resources.condition_prone
import org.dembeyo.shared.resources.condition_restrained
import org.dembeyo.shared.resources.condition_stunned
import org.dembeyo.shared.resources.condition_unconscious
import org.dembeyo.shared.resources.construct
import org.dembeyo.shared.resources.damage_acid
import org.dembeyo.shared.resources.damage_bludgeoning
import org.dembeyo.shared.resources.damage_cold
import org.dembeyo.shared.resources.damage_fire
import org.dembeyo.shared.resources.damage_force
import org.dembeyo.shared.resources.damage_lightning
import org.dembeyo.shared.resources.damage_necrotic
import org.dembeyo.shared.resources.damage_piercing
import org.dembeyo.shared.resources.damage_poison
import org.dembeyo.shared.resources.damage_psychic
import org.dembeyo.shared.resources.damage_radiant
import org.dembeyo.shared.resources.damage_slashing
import org.dembeyo.shared.resources.damage_thunder
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
import org.dembeyo.shared.resources.legendary
import org.dembeyo.shared.resources.medium
import org.dembeyo.shared.resources.monstrosity
import org.dembeyo.shared.resources.neutral
import org.dembeyo.shared.resources.neutral_evil
import org.dembeyo.shared.resources.neutral_good
import org.dembeyo.shared.resources.ooze
import org.dembeyo.shared.resources.plant
import org.dembeyo.shared.resources.rare
import org.dembeyo.shared.resources.small
import org.dembeyo.shared.resources.tiny
import org.dembeyo.shared.resources.titanic
import org.dembeyo.shared.resources.unaligned
import org.dembeyo.shared.resources.uncommon
import org.dembeyo.shared.resources.undead
import org.dembeyo.shared.resources.varies
import org.dembeyo.shared.resources.very_rare
import org.jetbrains.compose.resources.StringResource

fun Rarity.stringRes(): StringResource = when (this) {
    Rarity.COMMON -> Res.string.common
    Rarity.UNCOMMON -> Res.string.uncommon
    Rarity.RARE -> Res.string.rare
    Rarity.VERY_RARE -> Res.string.very_rare
    Rarity.LEGENDARY -> Res.string.legendary
    Rarity.ARTIFACT -> Res.string.artifact
    Rarity.VARIES -> Res.string.varies
}

fun Rarity.getRarityColor(): Color = when (this) {
    Rarity.COMMON -> Color(0xFFB69470)
    Rarity.UNCOMMON -> Color(0xFFCD8E4B)
    Rarity.RARE -> Color(0xFFEABA25)
    Rarity.VERY_RARE -> Color(0xFFEF9315)
    Rarity.LEGENDARY -> Color(0xFFEF5B15)
    Rarity.ARTIFACT -> Color(0xFFD61070)
    Rarity.VARIES -> Color(0xFFD7C1CC)
}

fun Ability.stringRes(): StringResource = when (this) {
    Ability.CHA -> Res.string.ability_cha
    Ability.CON -> Res.string.ability_con
    Ability.DEX -> Res.string.ability_dex
    Ability.INT -> Res.string.ability_int
    Ability.STR -> Res.string.ability_str
    Ability.WIS -> Res.string.ability_wis
}

@Composable
fun <T> List<T>.joinToString(separator: String, transform: @Composable (T) -> String): String {
    return this.map { transform(it) }.fastJoinToString(separator)
}

fun Condition.stringRes(): StringResource = when (this) {
    Condition.BLINDED -> Res.string.condition_blinded
    Condition.CHARMED -> Res.string.condition_charmed
    Condition.DEAFENED -> Res.string.condition_deafened
    Condition.FRIGHTENED -> Res.string.condition_frightened
    Condition.GRAPPLED -> Res.string.condition_grappled
    Condition.INCAPACITATED -> Res.string.condition_incapacitated
    Condition.INVISIBLE -> Res.string.condition_invisible
    Condition.PARALYZED -> Res.string.condition_paralyzed
    Condition.EXHAUSTION -> Res.string.condition_exhaustion
    Condition.PETRIFIED -> Res.string.condition_petrified
    Condition.POISONED -> Res.string.condition_prone
    Condition.PRONE -> Res.string.condition_prone
    Condition.RESTRAINED -> Res.string.condition_restrained
    Condition.STUNNED -> Res.string.condition_stunned
    Condition.UNCONSCIOUS -> Res.string.condition_unconscious
}

fun DamageType.stringRes(): StringResource = when (this) {
    DamageType.ACID -> Res.string.damage_acid
    DamageType.BLUDGEONING -> Res.string.damage_bludgeoning
    DamageType.COLD -> Res.string.damage_cold
    DamageType.FIRE -> Res.string.damage_fire
    DamageType.FORCE -> Res.string.damage_force
    DamageType.LIGHTNING -> Res.string.damage_lightning
    DamageType.NECROTIC -> Res.string.damage_necrotic
    DamageType.PIERCING -> Res.string.damage_piercing
    DamageType.SLASHING -> Res.string.damage_slashing
    DamageType.THUNDER -> Res.string.damage_thunder
    DamageType.POISON -> Res.string.damage_poison
    DamageType.PSYCHIC -> Res.string.damage_psychic
    DamageType.RADIANT -> Res.string.damage_radiant
}

fun Int.getAbilityBonusColor(): Color = when (this) {
    in -30..-25 -> Color(0xFF4B0082) // Indigo (Dark Purple)
    in -24..-20 -> Color(0xFF8A2BE2) // Blue Violet
    in -19..-15 -> Color(0xFF9370DB) // Medium Purple
    in -14..-10 -> Color(0xFFBA55D3) // Orchid
    in -9..-5 -> Color(0xFFDDA0DD) // Plum
    in -4..-1 -> Color(0xFFE6E6FA) // Lavender
    0 -> Color(0xFFFFFFFF) // White
    in 1..5 -> Color(0xFFFFE5E5) // Very Pale Pink
    in 6..10 -> Color(0xFFFFCCCC) // Pale Pink
    in 11..15 -> Color(0xFFFF9999) // Soft Pinkish Red
    in 16..20 -> Color(0xFFFF6666) // Medium Red
    in 21..25 -> Color(0xFFFF4D4D) // Brighter Red
    in 26..30 -> Color(0xFFFF3333) // Strong Red
    in 31..35 -> Color(0xFFFF1A1A) // Deep Red
    in 36..40 -> Color(0xFFFF0000) // Pure Red
    else -> throw IllegalArgumentException("Invalid ability bonus value: $this, no color found")
}

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
        Alignment.AnyGoodAlignment -> Res.string.any_good_alignment
        Alignment.AnyNonLawfulAlignment -> Res.string.any_non_lawful_alignment
        Alignment.AnyChaoticAlignment -> Res.string.any_chaotic_alignment
        Alignment.AnyNonGoodAlignment -> Res.string.any_non_good_alignment
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
        CreatureSize.Titanic -> Res.string.titanic
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

    // Interpolate colors from pale green to dark red-purple
    val red = (128 + (127 * normalized)).toInt() // Starts from pale (128) to full (255)
    val green = (255 * (1 - normalized)).toInt() // Full green fades to zero
    val blue = (128 * normalized).toInt() // Adds purple component as it increases

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