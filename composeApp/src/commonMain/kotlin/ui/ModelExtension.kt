package ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.util.fastJoinToString
import domain.model.Ability
import domain.model.Alignment
import domain.model.Condition
import domain.model.DamageType
import domain.model.Level
import domain.model.character.CharacterClass
import domain.model.magicItem.ItemCategory
import domain.model.magicItem.ItemRarity
import domain.model.monster.Challenge
import domain.model.monster.CreatureSize
import domain.model.monster.CreatureType
import domain.model.spell.MagicSchool
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.aberration
import org.dembeyo.shared.resources.ability_cha
import org.dembeyo.shared.resources.ability_con
import org.dembeyo.shared.resources.ability_dex
import org.dembeyo.shared.resources.ability_int
import org.dembeyo.shared.resources.ability_str
import org.dembeyo.shared.resources.ability_wis
import org.dembeyo.shared.resources.abjuration
import org.dembeyo.shared.resources.any_alignment
import org.dembeyo.shared.resources.any_chaotic_alignment
import org.dembeyo.shared.resources.any_evil_alignment
import org.dembeyo.shared.resources.any_good_alignment
import org.dembeyo.shared.resources.any_non_good_alignment
import org.dembeyo.shared.resources.any_non_lawful_alignment
import org.dembeyo.shared.resources.artifact
import org.dembeyo.shared.resources.barbarian_class
import org.dembeyo.shared.resources.bard_class
import org.dembeyo.shared.resources.beast
import org.dembeyo.shared.resources.category_adventuring_gear
import org.dembeyo.shared.resources.category_ammunition
import org.dembeyo.shared.resources.category_armor
import org.dembeyo.shared.resources.category_art
import org.dembeyo.shared.resources.category_drawn_vehicle
import org.dembeyo.shared.resources.category_gem
import org.dembeyo.shared.resources.category_jewelry
import org.dembeyo.shared.resources.category_poison
import org.dembeyo.shared.resources.category_potion
import org.dembeyo.shared.resources.category_ring
import org.dembeyo.shared.resources.category_rod
import org.dembeyo.shared.resources.category_scroll
import org.dembeyo.shared.resources.category_shield
import org.dembeyo.shared.resources.category_staff
import org.dembeyo.shared.resources.category_tools
import org.dembeyo.shared.resources.category_trade_good
import org.dembeyo.shared.resources.category_wand
import org.dembeyo.shared.resources.category_waterborne_vehicle
import org.dembeyo.shared.resources.category_weapon
import org.dembeyo.shared.resources.category_wondrous_item
import org.dembeyo.shared.resources.celestial
import org.dembeyo.shared.resources.chaotic_evil
import org.dembeyo.shared.resources.chaotic_good
import org.dembeyo.shared.resources.chaotic_neutral
import org.dembeyo.shared.resources.cleric_class
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
import org.dembeyo.shared.resources.conjuration
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
import org.dembeyo.shared.resources.damage_slash
import org.dembeyo.shared.resources.damage_slashing
import org.dembeyo.shared.resources.damage_thunder
import org.dembeyo.shared.resources.divination
import org.dembeyo.shared.resources.dragon
import org.dembeyo.shared.resources.drop_down_option_empty
import org.dembeyo.shared.resources.druid_class
import org.dembeyo.shared.resources.elemental
import org.dembeyo.shared.resources.enchantment
import org.dembeyo.shared.resources.evocation
import org.dembeyo.shared.resources.fey
import org.dembeyo.shared.resources.fiend
import org.dembeyo.shared.resources.fighter_class
import org.dembeyo.shared.resources.gargantuan
import org.dembeyo.shared.resources.giant
import org.dembeyo.shared.resources.huge
import org.dembeyo.shared.resources.humanoid
import org.dembeyo.shared.resources.illusion
import org.dembeyo.shared.resources.large
import org.dembeyo.shared.resources.lawful_evil
import org.dembeyo.shared.resources.lawful_good
import org.dembeyo.shared.resources.lawful_neutral
import org.dembeyo.shared.resources.legendary
import org.dembeyo.shared.resources.medium
import org.dembeyo.shared.resources.monk_class
import org.dembeyo.shared.resources.monstrosity
import org.dembeyo.shared.resources.necromancy
import org.dembeyo.shared.resources.neutral
import org.dembeyo.shared.resources.neutral_evil
import org.dembeyo.shared.resources.neutral_good
import org.dembeyo.shared.resources.none
import org.dembeyo.shared.resources.ooze
import org.dembeyo.shared.resources.paladin_class
import org.dembeyo.shared.resources.plant
import org.dembeyo.shared.resources.ranger_class
import org.dembeyo.shared.resources.rare
import org.dembeyo.shared.resources.rogue_class
import org.dembeyo.shared.resources.small
import org.dembeyo.shared.resources.sorcerer_class
import org.dembeyo.shared.resources.tiny
import org.dembeyo.shared.resources.titanic
import org.dembeyo.shared.resources.transmutation
import org.dembeyo.shared.resources.unaligned
import org.dembeyo.shared.resources.uncommon
import org.dembeyo.shared.resources.undead
import org.dembeyo.shared.resources.very_rare
import org.dembeyo.shared.resources.warlock_class
import org.dembeyo.shared.resources.wizard_class
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

fun CharacterClass.stringRes(): StringResource = when (this) {
    CharacterClass.BARBARIAN -> Res.string.barbarian_class
    CharacterClass.BARD -> Res.string.bard_class
    CharacterClass.CLERIC -> Res.string.cleric_class
    CharacterClass.DRUID -> Res.string.druid_class
    CharacterClass.FIGHTER -> Res.string.fighter_class
    CharacterClass.MONK -> Res.string.monk_class
    CharacterClass.PALADIN -> Res.string.paladin_class
    CharacterClass.RANGER -> Res.string.ranger_class
    CharacterClass.ROGUE -> Res.string.rogue_class
    CharacterClass.SORCERER -> Res.string.sorcerer_class
    CharacterClass.WARLOCK -> Res.string.warlock_class
    CharacterClass.WIZARD -> Res.string.wizard_class
}

fun MagicSchool.stringRes(): StringResource = when (this) {
    MagicSchool.ABJURATION -> Res.string.abjuration
    MagicSchool.CONJURATION -> Res.string.conjuration
    MagicSchool.DIVINATION -> Res.string.divination
    MagicSchool.ENCHANTMENT -> Res.string.enchantment
    MagicSchool.EVOCATION -> Res.string.evocation
    MagicSchool.ILLUSION -> Res.string.illusion
    MagicSchool.NECROMANCY -> Res.string.necromancy
    MagicSchool.TRANSMUTATION -> Res.string.transmutation
}

fun MagicSchool.color(): Color = when (this) {
    MagicSchool.ABJURATION -> Color(0xFFAEDFF7) // Pastel Blue
    MagicSchool.CONJURATION -> Color(0xFFD8BFD8) // Pastel Purple
    MagicSchool.DIVINATION -> Color(0xFFFFFFFF) // White
    MagicSchool.ENCHANTMENT -> Color(0xFFFFE4E1) // Pastel Pink
    MagicSchool.EVOCATION -> Color(0xFFFFA07A) // Pastel Red
    MagicSchool.ILLUSION -> Color(0xFFE0E0E0) // Pastel Silver
    MagicSchool.NECROMANCY -> Color(0xFFA9A9A9) // Pastel Black (Grey)
    MagicSchool.TRANSMUTATION -> Color(0xFF90EE90) // Pastel Green
}

fun ItemRarity?.stringRes(): StringResource = when (this) {
    ItemRarity.NONE -> Res.string.none
    ItemRarity.COMMON -> Res.string.common
    ItemRarity.UNCOMMON -> Res.string.uncommon
    ItemRarity.RARE -> Res.string.rare
    ItemRarity.VERY_RARE -> Res.string.very_rare
    ItemRarity.LEGENDARY -> Res.string.legendary
    ItemRarity.ARTIFACT -> Res.string.artifact
    null -> Res.string.drop_down_option_empty
}

fun ItemCategory.stringRes(): StringResource = when (this) {
    ItemCategory.RING -> Res.string.category_ring
    ItemCategory.ADVENTURING_GEAR -> Res.string.category_adventuring_gear
    ItemCategory.AMMUNITION -> Res.string.category_ammunition
    ItemCategory.ARMOR -> Res.string.category_armor
    ItemCategory.ART -> Res.string.category_art
    ItemCategory.DRAWN_VEHICLE -> Res.string.category_drawn_vehicle
    ItemCategory.GEM -> Res.string.category_gem
    ItemCategory.JEWELRY -> Res.string.category_jewelry
    ItemCategory.POISON -> Res.string.category_poison
    ItemCategory.POTION -> Res.string.category_potion
    ItemCategory.ROD -> Res.string.category_rod
    ItemCategory.SCROLL -> Res.string.category_scroll
    ItemCategory.SHIELD -> Res.string.category_shield
    ItemCategory.STAFF -> Res.string.category_staff
    ItemCategory.TOOLS -> Res.string.category_tools
    ItemCategory.TRADE_GOOD -> Res.string.category_trade_good
    ItemCategory.WAND -> Res.string.category_wand
    ItemCategory.WATERBORNE_VEHICLE -> Res.string.category_waterborne_vehicle
    ItemCategory.WEAPON -> Res.string.category_weapon
    ItemCategory.WONDROUS_ITEM -> Res.string.category_wondrous_item
}

fun ItemRarity.getRarityColor(): Color = when (this) {
    ItemRarity.NONE -> Color(0xFF808080) // Grayscale
    ItemRarity.COMMON -> Color(0xFFB69470)
    ItemRarity.UNCOMMON -> Color(0xFFCD8E4B)
    ItemRarity.RARE -> Color(0xFFEABA25)
    ItemRarity.VERY_RARE -> Color(0xFFEF9315)
    ItemRarity.LEGENDARY -> Color(0xFFEF5B15)
    ItemRarity.ARTIFACT -> Color(0xFFD61070)
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

fun DamageType.iconRes(): DrawableResource = when (this) {
    DamageType.ACID -> Res.drawable.damage_acid
    DamageType.BLUDGEONING -> Res.drawable.damage_bludgeoning
    DamageType.COLD -> Res.drawable.damage_cold
    DamageType.FIRE -> Res.drawable.damage_fire
    DamageType.FORCE -> Res.drawable.damage_force
    DamageType.LIGHTNING -> Res.drawable.damage_lightning
    DamageType.NECROTIC -> Res.drawable.damage_necrotic
    DamageType.PIERCING -> Res.drawable.damage_piercing
    DamageType.SLASHING -> Res.drawable.damage_slash
    DamageType.THUNDER -> Res.drawable.damage_thunder
    DamageType.POISON -> Res.drawable.damage_poison
    DamageType.PSYCHIC -> Res.drawable.damage_psychic
    DamageType.RADIANT -> Res.drawable.damage_radiant
}

fun DamageType.color(): Color = when (this) {
    DamageType.ACID -> Color(0xFF4CAF50) // Green for Acid
    DamageType.BLUDGEONING -> Color(0xFF795548) // Brown for Bludgeoning
    DamageType.COLD -> Color(0xFF03A9F4) // Light Blue for Cold
    DamageType.FIRE -> Color(0xFFFF5722) // Orange-Red for Fire
    DamageType.FORCE -> Color(0xFF9C27B0) // Purple for Force
    DamageType.LIGHTNING -> Color(0xFFFFEB3B) // Yellow for Lightning
    DamageType.NECROTIC -> Color(0xFF212121) // Dark Grey for Necrotic
    DamageType.PIERCING -> Color(0xFF607D8B) // Blue-Grey for Piercing
    DamageType.SLASHING -> Color(0xFFD32F2F) // Red for Slashing
    DamageType.THUNDER -> Color(0xFF673AB7) // Deep Purple for Thunder
    DamageType.POISON -> Color(0xFF8BC34A) // Lime Green for Poison
    DamageType.PSYCHIC -> Color(0xFFE91E63) // Pink for Psychic
    DamageType.RADIANT -> Color(0xFFFFF176) // Light Yellow for Radiant
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