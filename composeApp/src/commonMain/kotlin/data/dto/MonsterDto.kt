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
    val armorClass: List<ArmorClassDto>? = null,
    val hitPoints: Int? = null,
    val hitDice: String? = null,
    val hitPointsRoll: String? = null,
    val speed: SpeedDto? = null,
    val strength: Int? = null,
    val dexterity: Int? = null,
    val constitution: Int? = null,
    val intelligence: Int? = null,
    val wisdom: Int? = null,
    val charisma: Int? = null,
    val proficiencies: List<ProficiencyDto>? = null,
    val damageVulnerabilities: List<String>? = null,
    val damageResistances: List<String>? = null,
    val damageImmunities: List<String>? = null,
    val conditionImmunities: List<ReferenceDto>? = null,
    val senses: SensesDto? = null,
    val languages: String? = null,
    val challengeRating: Double? = null,
    val proficiencyBonus: Int? = null,
    val xp: Int? = null,
    val specialAbilities: List<SpecialAbilityDto>? = null,
    val actions: List<ActionDto>? = null,
    val image: String? = null,
    val legendaryActions: List<ActionDto>? = null
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
        val passivePerception: Int
    )

    @Serializable
    data class SpecialAbilityDto(
        val name: String,
        val desc: String
    )

    @Serializable
    data class ActionDto(
        val name: String,
        val multiattackType: String? = null,
        val desc: String,
        val actions: List<SubActionDto> = emptyList(),
        val attackBonus: Int? = null,
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
        val damageType: ReferenceDto,
        val damageDice: String
    )
}

