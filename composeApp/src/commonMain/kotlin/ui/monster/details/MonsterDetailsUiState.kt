package ui.monster.details

import domain.model.Alignment
import domain.model.CreatureSize
import domain.model.CreatureType
import domain.model.monster.Action
import domain.model.monster.Challenge

data class MonsterDetailsUiState(
    val isReady: Boolean = false,
    val slug: String? = null,
    val name: String? = null,
    val challenge: Challenge = Challenge.CR_0,
    val isFavorite: Boolean = false,
    val size: CreatureSize = CreatureSize.Tiny,
    val type: CreatureType = CreatureType.DRAGON,
    val alignment: Alignment = Alignment.Neutral,
    val armorsClass: Int = 0,
    val armorDesc: String? = null,
    val hitPoints: Int = 0,
    val hitDice: String? = null,
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
    val skills: Map<String, Int> = emptyMap(),
    val damageVulnerabilities: String = "",
    val damageResistances: String = "",
    val damageImmunities: String = "",
    val conditionImmunities: String = "",
    val senses: String = "",
    val languages: String = "",
    val specialAbilities: List<Action> = emptyList(),
    val actions: List<Action> = emptyList(),
    val bonusActions: List<Action> = emptyList(),
    val reactions: List<Action> = emptyList(),
    val legendaryDesc: String? = null,
    val legendaryActions: List<Action> = emptyList(),
    val error: String? = null
) {
    val hasSavingThrows: Boolean
        get() = strengthSave != null ||
                dexteritySave != null ||
                constitutionSave != null ||
                intelligenceSave != null ||
                wisdomSave != null ||
                charismaSave != null
}