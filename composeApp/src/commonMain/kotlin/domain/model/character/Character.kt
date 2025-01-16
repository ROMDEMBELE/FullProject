package domain.model.character

import domain.model.Alignment
import domain.model.Level

data class Character(
    val uuid: String,
    val name: String,
    val level: Level,
    val alignment: Alignment,
    val characterClass: CharacterClass,
    val armorClass: Int,
    val passivePerception: Int,
    val hitPoint: Int,
    val charisma: Int,
    val dexterity: Int,
    val constitution: Int,
    val intelligence: Int,
    val strength: Int,
    val wisdom: Int,
)