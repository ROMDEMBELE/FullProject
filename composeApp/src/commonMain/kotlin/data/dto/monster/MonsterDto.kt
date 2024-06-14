package data.dto.monster

import data.dto.ReferenceDto
import domain.Alignment
import domain.monster.CreatureMovement
import domain.monster.CreatureSense
import domain.monster.CreatureSize
import domain.monster.CreatureType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MonsterDto(
    val index: String,
    val name: String,
    val url: String,
    val size: CreatureSize,
    val type: CreatureType,
    val alignment: Alignment,
    @SerialName("armor_class")
    val armorClass: List<ArmorDto>,
    @SerialName("hit_points")
    val hitPoints: Int,
    @SerialName("hit_dice")
    val hitDice: String,
    @SerialName("hit_points_roll")
    val hitPointsRoll: String,
    val speed: Map<CreatureMovement, String>,
    val strength: Int,
    val dexterity: Int,
    val constitution: Int,
    val intelligence: Int,
    val wisdom: Int,
    val charisma: Int,
    val proficiencies: List<ProficiencyDto>,
    @SerialName("damage_vulnerabilities")
    val damageVulnerabilities: List<String>,
    @SerialName("damage_resistances")
    val damageResistances: List<String>,
    @SerialName("damage_immunities")
    val damageImmunities: List<String>,
    @SerialName("condition_immunities")
    val conditionImmunities: List<ReferenceDto>,
    val senses: Map<CreatureSense, String>,
    val languages: String,
    @SerialName("challenge_rating")
    val challengeRating: Double,
    @SerialName("proficiency_bonus")
    val proficiencyBonus: Int,
    val xp: Int,
    @SerialName("special_abilities")
    val specialAbilities: List<PolymorphicAbility>,
    val actions: List<PolymorphicAction>,
    val image: String? = null,
    @SerialName("legendary_actions")
    val legendaryActions: List<PolymorphicAction>
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

