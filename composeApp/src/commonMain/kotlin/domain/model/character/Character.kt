package domain.model.character

import domain.model.Level

data class Character(
    val id: Long? = null,  // ID can be null if it's not assigned yet
    val fullName: String,
    val player: String,
    val profilePicture: String? = null,
    val level: Level,
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