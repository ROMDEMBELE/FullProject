package data.dto

import kotlinx.serialization.Serializable

@Serializable
data class MonsterDto(
    val index: String,
    val name: String,
    val url: String,
    val size: String? = null,
    val type: String? = null,
    val alignment: String? = null,
    val armor_class: List<ArmorClassDto>? = null,
    val hit_points: Int? = null,
    val hit_dice: String? = null,
    val hit_points_roll: String? = null,
    val speed: SpeedDto? = null,
    val strength: Int? = null,
    val dexterity: Int? = null,
    val constitution: Int? = null,
    val intelligence: Int? = null,
    val wisdom: Int? = null,
    val charisma: Int? = null,
    val proficiencies: List<ProficiencyDto>? = null,
    val damage_vulnerabilities: List<String>? = null,
    val damage_resistances: List<String>? = null,
    val damage_immunities: List<String>? = null,
    val condition_immunities: List<ReferenceDto>? = null,
    val senses: SensesDto? = null,
    val languages: String? = null,
    val challenge_rating: Double? = null,
    val proficiency_bonus: Int? = null,
    val xp: Int? = null,
    val special_abilities: List<SpecialAbilityDto>? = null,
    val actions: List<ActionDto>? = null,
    val image: String? = null,
    val legendary_actions: List<ActionDto>? = null
) {
    @Serializable
    data class ArmorClassDto(
        val type: String,
        val value: Int
    )

    @Serializable
    data class SpeedDto(
        val walk: String? = null,
        val fly: String? = null,
        val swim: String? = null
    )

    @Serializable
    data class ProficiencyDto(
        val value: Int,
        val proficiency: ReferenceDto
    )

    @Serializable
    data class SensesDto(
        val darkVision: String? = null,
        val blindsight: String? = null,
        val passive_perception: Int
    )

    @Serializable
    data class SpecialAbilityDto(
        val name: String,
        val desc: String
    )

    @Serializable
    data class ActionDto(
        val name: String,
        val multiattack_type: String? = null,
        val desc: String,
        val actions: List<SubActionDto> = emptyList(),
        val attack_bonus: Int? = null,
        val damage: List<DamageDto> = emptyList()
    )

    @Serializable
    data class SubActionDto(
        val actionName: String,
        val count: Int,
        val type: String
    )

    @Serializable
    data class DamageDto(
        val damage_type: ReferenceDto,
        val damage_dice: String
    )
}

