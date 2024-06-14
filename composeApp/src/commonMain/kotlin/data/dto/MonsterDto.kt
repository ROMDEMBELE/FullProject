package data.dto

import domain.Monster
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MonsterDto(
    val index: String,
    val name: String,
    val url: String,
    val size: Monster.CreatureSize? = null,
    val type: Monster.CreatureType? = null,
    val alignment: Monster.Alignment? = null,
    @SerialName("armor_class")
    val armorClass: List<ArmorDto>? = null,
    @SerialName("hit_points")
    val hitPoints: Int? = null,
    @SerialName("hit_dice")
    val hitDice: String? = null,
    @SerialName("hit_points_roll")
    val hitPointsRoll: String? = null,
    val speed: Map<Monster.Movement, String>? = null,
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
    val senses: Map<Monster.Senses, String>? = null,
    val languages: String? = null,
    @SerialName("challenge_rating")
    val challengeRating: Double? = null,
    @SerialName("proficiency_bonus")
    val proficiencyBonus: Int? = null,
    val xp: Int? = null,
    @SerialName("special_abilities")
    val specialAbilities: List<PolymorphicAbility>? = null,
    val actions: List<PolymorphicAction>? = null,
    val image: String? = null,
    @SerialName("legendary_actions")
    val legendaryActions: List<PolymorphicAction>? = null
) {

    @Serializable
    data class ProficiencyDto(
        val value: Int,
        val proficiency: ReferenceDto
    )

    @Serializable
    data class ArmorDto(
        val value: Int,
        val type: String
    )
}

