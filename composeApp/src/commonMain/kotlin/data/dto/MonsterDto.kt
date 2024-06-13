package data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MonsterDto(
    val index: String,
    val name: String,
    val url: String,
    val size: String? = null,
    val type: String? = null,
    val alignment: String? = null,
    @SerialName("armor_class")
    val armorClass: List<ArmorClassDto>? = null,
    @SerialName("hit_points")
    val hitPoints: Int? = null,
    @SerialName("hit_dice")
    val hitDice: String? = null,
    @SerialName("hit_points_roll")
    val hitPointsRoll: String? = null,
    val speed: SpeedDto? = null,
    val strength: Int? = null,
    val dexterity: Int? = null,
    val constitution: Int? = null,
    val intelligence: Int? = null,
    val wisdom: Int? = null,
    val charisma: Int? = null,
    val proficiencies: List<ProficiencyDto>? = null,
    @SerialName("damage_vulnerabilities")
    val damageVulnerabilities: List<String>? = null,
    @SerialName("damage_resistances")
    val damageResistances: List<String>? = null,
    @SerialName("damage_immunities")
    val damageImmunities: List<String>? = null,
    @SerialName("condition_immunities")
    val conditionImmunities: List<ReferenceDto>? = null,
    val senses: SensesDto? = null,
    val languages: String? = null,
    @SerialName("challenge_rating")
    val challengeRating: Double? = null,
    @SerialName("proficiency_bonus")
    val proficiencyBonus: Int? = null,
    val xp: Int? = null,
    @SerialName("special_abilities")
    val specialAbilities: List<SpecialAbilityDto>? = null,
    val actions: List<ActionDto>? = null,
    val image: String? = null,
    val legendary_actions: List<ActionDto>? = null
) {
    @Serializable
    data class ArmorClassDto(
        val type: String,
        val value: Int
    ) {
        override fun toString(): String = "$value ($type)"
    }

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
        @SerialName("passive_perception")
        val passivePerception: Int
    ) {

        override fun toString(): String {
            return buildString {
                append("passive Perception $passivePerception")
                if (darkVision != null) append(", darkVision")
                if (blindsight != null) append(", blind sight")
            }
        }
    }

    @Serializable
    data class SpecialAbilityDto(
        val name: String,
        val desc: String
    )

    @Serializable
    data class ActionDto(
        val name: String,
        @SerialName("multiattack_type")
        val multiAttackType: String? = null,
        val desc: String,
        val usage: UsageDto? = null,
        val dc: DcDto? = null,
        val actions: List<SubActionDto>? = null,
        @SerialName("attack_bonus")
        val attackBonus: Int? = null,
        @SerialName("action_options")
        val multiAttackOption: MultiAttackOption? = null,
        val damage: List<DamageDto>? = null
    )

    @Serializable
    data class MultiAttackOption(
        val choose: Int,
        val type: String,
        val from: OptionSet<ActionOption>
    )

    @Serializable
    data class OptionSet<T>(
        val option_set_type: String,
        val options: List<T>
    )

    @Serializable
    data class ActionOption(
        @SerialName("option_type")
        val optionType: String,
        val items: List<ActionOption>? = null,
        @SerialName("action_name")
        val actionName: String? = null,
        val count: Int? = null,
        val type: String? = null
    )

    @Serializable
    data class DcDto(
        @SerialName("dc_type")
        val typeOfAbility: ReferenceDto,
        @SerialName("dc_value")
        val difficulty: Int,
        @SerialName("success_type")
        val success: String
    ) {
        override fun toString(): String {
            return "Save DC $difficulty (${typeOfAbility.name}) for $success"
        }
    }

    @Serializable
    data class UsageDto(
        val type: String,
        val dice: String,
        val min_value: Int,
    )

    @Serializable
    data class SubActionDto(
        val actionName: String,
        val count: Int,
        val type: String
    )

    @Serializable
    data class DamageDto(
        val choose: Int? = null,
        val type: String? = null,
        val from: OptionSet<DamageOption>? = null,
        @SerialName("damage_type")
        val damageType: ReferenceDto? = null,
        @SerialName("damage_dice")
        val damageDice: String? = null
    )

    @Serializable
    data class DamageOption(
        @SerialName("option_type")
        val type: String,
        @SerialName("damage_type")
        val damageType: ReferenceDto,
        @SerialName("damage_dice")
        val damageDice: String,
        val notes: String
    )
}

