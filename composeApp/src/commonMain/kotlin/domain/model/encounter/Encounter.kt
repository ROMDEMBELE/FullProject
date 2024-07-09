package domain.model.encounter

data class Encounter(
    val id: Long,
    val campaignId: Long,
    val title: String,
    val description: String,
    val fighter: List<EncounterFighter>,
    val turn: Int = 0,
    val isFinished: Boolean = true
)

abstract class EncounterFighter(
    val name: String,
    val order: Int,
    open val conditions: List<Condition> = emptyList(),
    val armorClass: Int,
    val spellSavingThrow: Int? = null,
    val maxHitPoint: Int,
    val hitPoint: Int,
    val charisma: Int,
    val dexterity: Int,
    val constitution: Int,
    val intelligence: Int,
    val strength: Int,
    val wisdom: Int,
)