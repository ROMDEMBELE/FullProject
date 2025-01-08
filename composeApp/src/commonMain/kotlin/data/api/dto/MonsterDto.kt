package data.api.dto

import domain.model.Alignment
import domain.model.Condition
import domain.model.CreatureSize
import domain.model.CreatureType
import domain.model.DamageType
import domain.model.Environment
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data Transfer Object for Monster.
 */
@Serializable
data class MonsterDto(
    @SerialName("key")
    val key: String,
    @SerialName("name")
    val name: String,
    @SerialName("size")
    val size: CreatureSize,
    @SerialName("walk")
    val walk: Double,
    @SerialName("swim")
    val swim: Double,
    @SerialName("fly")
    val fly: Double,
    @SerialName("crawl")
    val crawl: Double,
    @SerialName("hover")
    val hover: Boolean,
    @SerialName("burrow")
    val burrow: Double,
    @SerialName("climb")
    val climb: Double,
    @SerialName("type")
    val type: CreatureType,
    @SerialName("alignment")
    val alignment: Alignment,
    @SerialName("languages")
    val languages: List<String>,
    @SerialName("armor_class")
    val armorClass: Int,
    @SerialName("hit_points")
    val hitPoints: Int,
    @SerialName("hit_dice")
    val hitDice: String,
    @SerialName("challenge")
    val challenge: Double,
    @SerialName("strength")
    val strength: Int,
    @SerialName("dexterity")
    val dexterity: Int,
    @SerialName("constitution")
    val constitution: Int,
    @SerialName("intelligence")
    val intelligence: Int,
    @SerialName("wisdom")
    val wisdom: Int,
    @SerialName("charisma")
    val charisma: Int,
    @SerialName("strength_save")
    val strengthSave: Int? = null,
    @SerialName("dexterity_save")
    val dexteritySave: Int? = null,
    @SerialName("constitution_save")
    val constitutionSave: Int? = null,
    @SerialName("intelligence_save")
    val intelligenceSave: Int? = null,
    @SerialName("wisdom_save")
    val wisdomSave: Int? = null,
    @SerialName("charisma_save")
    val charismaSave: Int? = null,
    @SerialName("skills")
    val skills: SkillsDto,
    @SerialName("passive_perception")
    val passivePerception: Int,
    @SerialName("damage_immunities")
    val damageImmunities: List<DamageType>,
    @SerialName("nonmagical_attack_immunity")
    val nonMagicalAttackImmunity: Boolean,
    @SerialName("damage_resistances")
    val damageResistances: List<DamageType>,
    @SerialName("nonmagical_attack_resistance")
    val nonMagicalAttackResistance: Boolean,
    @SerialName("damage_vulnerabilities")
    val damageVulnerabilities: List<DamageType>,
    @SerialName("condition_immunities")
    val conditionImmunities: List<Condition>,
    @SerialName("normal_sight_range")
    val normalSightRange: Double,
    @SerialName("darkvision_range")
    val darkVisionRange: Double?,
    @SerialName("blindsight_range")
    val blindSightRange: Double?,
    @SerialName("tremorsense_range")
    val tremorSenseRange: Double?,
    @SerialName("truesight_range")
    val trueSightRange: Double?,
    @SerialName("actions")
    val actions: List<ActionDto>,
    @SerialName("traits")
    val traits: List<TraitDto>,
    @SerialName("environments")
    val environments: List<Environment>
) {

    @Serializable
    data class SkillsDto(
        @SerialName("acrobatics")
        val acrobatics: Int? = null,
        @SerialName("animal_handling")
        val animalHandling: Int? = null,
        @SerialName("arcana")
        val arcana: Int? = null,
        @SerialName("athletics")
        val athletics: Int? = null,
        @SerialName("deception")
        val deception: Int? = null,
        @SerialName("history")
        val history: Int? = null,
        @SerialName("insight")
        val insight: Int? = null,
        @SerialName("intimidation")
        val intimidation: Int? = null,
        @SerialName("investigation")
        val investigation: Int? = null,
        @SerialName("medicine")
        val medicine: Int? = null,
        @SerialName("nature")
        val nature: Int? = null,
        @SerialName("perception")
        val perception: Int? = null,
        @SerialName("performance")
        val performance: Int? = null,
        @SerialName("persuasion")
        val persuasion: Int? = null,
        @SerialName("religion")
        val religion: Int? = null,
        @SerialName("sleight_of_hand")
        val sleightOfHand: Int? = null,
        @SerialName("stealth")
        val stealth: Int? = null,
        @SerialName("survival")
        val survival: Int? = null
    )

    @Serializable
    data class ActionDto(
        @SerialName("name")
        val name: String,
        @SerialName("desc")
        val description: String,
        @SerialName("usage")
        val usage: Usage,
        @SerialName("action_type")
        val actionType: ActionType,
        @SerialName("recharge_dice")
        val rechargeDice: String? = null,
        @SerialName("legendary_cost")
        val legendaryCost: Int? = null,
        @SerialName("attacks")
        val attacks: List<AttackDto> = emptyList()
    )

    @Serializable
    enum class ActionType {
        @SerialName("action")
        ACTION,

        @SerialName("reaction")
        REACTION,

        @SerialName("bonus")
        BONUS_ACTION,

        @SerialName("legendary")
        LEGENDARY_ACTION
    }

    @Serializable
    enum class Usage {
        @SerialName("once")
        ONCE,

        @SerialName("once_per_day")
        ONCE_PER_DAY,

        @SerialName("once_per_long_rest")
        ONCE_PER_LONG_REST,

        @SerialName("once_per_short_rest")
        ONCE_PER_SHORT_REST,

        @SerialName("recharge_with_dice")
        RECHARGE_WITH_DICE,

        @SerialName("at_will")
        AT_WILL
    }

    @Serializable
    data class AttackDto(
        @SerialName("name")
        val name: String,
        @SerialName("attack_type")
        val attackType: String,
        @SerialName("attack_bonus")
        val attackBonus: Int,
        @SerialName("reach")
        val reach: Double?,
        @SerialName("range")
        val range: Double?,
        @SerialName("long_range")
        val longRange: Double?,
        @SerialName("damage_dice")
        val damageDieCount: String,
        @SerialName("damage_bonus")
        val damageBonus: Int?,
        @SerialName("damage_type")
        val damageType: DamageType,
    )

    @Serializable
    data class TraitDto(
        @SerialName("name")
        val name: String,
        @SerialName("desc")
        val description: String,
    )
}

