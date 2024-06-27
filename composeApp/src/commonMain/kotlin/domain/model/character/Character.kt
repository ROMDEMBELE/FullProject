package domain.model.character

import domain.model.Level

data class Character(
    val id: Long,
    val campaignId: Long,
    val fullName: String,
    val player: String,
    val level: Level,
    val speciesId: Long,
    val species: Species? = null,
    val characterClass: String,
    val backgroundId: Long,
    val background: Background? = null,
    val armorClass: Int,
    val spellSavingThrow: Int,
    val hitPoint: Int,
    val charisma: Int,
    val dexterity: Int,
    val constitution: Int,
    val intelligence: Int,
    val strength: Int,
    val wisdom: Int,
)