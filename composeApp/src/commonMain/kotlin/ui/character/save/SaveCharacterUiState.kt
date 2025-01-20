package ui.character.save

import androidx.compose.ui.text.input.TextFieldValue
import domain.model.Level
import domain.model.character.CharacterClass

data class SaveCharacterUiState(
    val index: String? = null,
    val characterName: TextFieldValue = TextFieldValue(),
    val characterClass: CharacterClass? = null,
    val level: Level = Level.LEVEL_1,
    val armorClass: Int = 10,
    val passivePerception: Int = 10,
    val hitPoint: Int = 1,
    val intelligence: Int = 10,
    val wisdom: Int = 10,
    val strength: Int = 10,
    val dexterity: Int = 10,
    val constitution: Int = 10,
    val charisma: Int = 10,
    val isReady: Boolean = false,
) {

    val canBeDeleted: Boolean = false

    val isValid: Boolean = false
}
