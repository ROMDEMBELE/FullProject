package ui.monster.details

import domain.model.Alignment
import domain.model.Condition
import domain.model.DamageType
import domain.model.character.Skill
import domain.model.monster.Action
import domain.model.monster.Challenge
import domain.model.monster.CreatureSize
import domain.model.monster.CreatureType
import domain.model.monster.Trait

data class MonsterDetailsUiState(
    val isReady: Boolean = false,
    val key: String? = null,
    val name: String? = null,
    val challenge: Challenge = Challenge.CR_0,
    val isFavorite: Boolean = false,
    val size: CreatureSize = CreatureSize.Tiny,
    val type: CreatureType = CreatureType.DRAGON,
    val alignment: Alignment = Alignment.Neutral,
    val armorsClass: Int = 0,
    val hitPoints: Int = 0,
    val walkSpeed: Double = 0.0,
    val swimSpeed: Double = 0.0,
    val flySpeed: Double = 0.0,
    val burrowSpeed: Double = 0.0,
    val climbSpeed: Double = 0.0,
    val hover: Boolean = false,
    val charisma: Int = 0,
    val charismaSave: Int? = null,
    val dexterity: Int = 0,
    val dexteritySave: Int? = null,
    val constitution: Int = 0,
    val constitutionSave: Int? = null,
    val intelligence: Int = 0,
    val intelligenceSave: Int? = null,
    val strength: Int = 0,
    val strengthSave: Int? = null,
    val wisdom: Int = 0,
    val wisdomSave: Int? = null,
    val skills: Map<Skill, Int> = emptyMap(),
    val nonMagicalAttackResistance: Boolean = false,
    val nonMagicalAttackImmunity: Boolean = false,
    val damageVulnerabilities: List<DamageType> = emptyList(),
    val damageResistances: List<DamageType> = emptyList(),
    val damageImmunities: List<DamageType> = emptyList(),
    val conditionImmunities: List<Condition> = emptyList(),
    val passivePerception: Int = 0,
    val darkVision: Double? = null,
    val trueSight: Double? = null,
    val tremorSense: Double? = null,
    val blindSight: Double? = null,
    val languages: List<String> = emptyList(),
    val trait: List<Trait> = emptyList(),
    val actions: List<Action> = emptyList(),
    val bonusActions: List<Action> = emptyList(),
    val reactions: List<Action> = emptyList(),
    val legendaryActions: List<Action> = emptyList(),
) {

    val hasSavingThrows: Boolean
        get() = strengthSave != null ||
                dexteritySave != null ||
                constitutionSave != null ||
                intelligenceSave != null ||
                wisdomSave != null ||
                charismaSave != null

    val hasVulnerabilities: Boolean
        get() = damageVulnerabilities.isNotEmpty()

    val hasResistances: Boolean
        get() = damageResistances.isNotEmpty()

    val hasImmunities: Boolean
        get() = damageImmunities.isNotEmpty()

    val hasConditionImmunities: Boolean
        get() = conditionImmunities.isNotEmpty()


}