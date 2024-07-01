package domain.model.monster

import domain.model.Alignment
import domain.model.SavingThrow

data class Monster(
    val index: String,
    val name: String,
    val challenge: Challenge,
    val isFavorite: Boolean = false,
    val details: Details? = null,
) {
    class Details(
        val size: CreatureSize,
        val type: CreatureType,
        val alignment: Alignment,
        val armorsClass: Map<String, Int>,
        val hitPoints: Int,
        val hitPointsRoll: String,
        val speedByMovements: Map<CreatureMovement, String>,
        val charisma: Int,
        val dexterity: Int,
        val constitution: Int,
        val intelligence: Int,
        val strength: Int,
        val wisdom: Int,
        val savingThrows: List<SavingThrow>,
        val skills: List<String>,
        val damageVulnerabilities: List<String>,
        val damageResistances: List<String>,
        val damageImmunities: List<String>,
        val conditionImmunities: List<String>,
        val senses: Map<CreatureSense, String>,
        val languages: String,
        val xp: Int,
        val specialAbilities: List<SpecialAbility>,
        val actions: List<Action>,
        val legendaryActions: List<Action>,
        val image: String? = null
    )
}

