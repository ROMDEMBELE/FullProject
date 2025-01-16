package ui.character.save

import androidx.compose.ui.text.input.TextFieldValue
import domain.model.Ability
import domain.model.Level
import domain.model.character.CharacterClass

data class SaveCharacterUiState(
    val id: Long? = null,
    val characterName: TextFieldValue = TextFieldValue(),
    val characterClass: CharacterClass? = null,
    val level: Level = Level.LEVEL_1,
    val armorClass: Int = 0,
    val passivePerception: Int = 10,
    val hitPoint: Int = 1,
    val abilities: Map<Ability, Int> = buildMap { Ability.entries.map { put(it, 10) } },
    val isReady: Boolean = false,
) {

    val canBeDeleted: Boolean
        get() {
            return id != null
        }

    val isValid: Boolean
        get() {
            if (characterName.text.isEmpty()) return false
            if (hitPoint < 1) return false

            return true
        }
}
