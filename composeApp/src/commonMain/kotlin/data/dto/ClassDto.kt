package data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ClassDto(
    val index: String,
    val name: String,
    val hitDie: Int? = null,
    val proficiencyChoices: List<ProficiencyChoiceDto>? = null,
    val startingEquipment: List<StartingEquipmentDto>? = null,
    val startingEquipmentOptions: List<StartingEquipmentOptionDto>? = null,
    val classLevels: String? = null,
    val multiClassing: MultiClassingDto? = null,
) {
    @Serializable
    data class ProficiencyChoiceDto(
        val desc: String,
        val choose: Int,
        val type: String,
        val from: OptionSetDto
    )

    @Serializable
    data class OptionSetDto(
        val optionSetType: String,
        val options: List<OptionDto>
    )

    @Serializable
    data class OptionDto(
        val optionType: String,
        val count: Int? = null,
        val choice: ChoiceDto? = null
    )

    @Serializable
    data class ChoiceDto(
        val desc: String,
        val choose: Int,
        val type: String,
        val from: OptionSetDto
    )

    @Serializable
    data class StartingEquipmentDto(
        val quantity: Int
    )

    @Serializable
    data class StartingEquipmentOptionDto(
        val desc: String,
        val choose: Int,
        val type: String,
        val from: OptionSetDto
    )

    @Serializable
    data class MultiClassingDto(
        val prerequisites: List<MultiClassingPrerequisiteDto>,
    )

    @Serializable
    data class MultiClassingPrerequisiteDto(
        val minimumScore: Int
    )
}

