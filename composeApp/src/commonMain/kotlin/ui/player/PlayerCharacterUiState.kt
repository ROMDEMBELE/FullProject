package ui.player

import androidx.compose.ui.text.input.TextFieldValue
import domain.model.Ability

data class PlayerCharacterUiState(
    private val id: Int = 0,
    val playerName: TextFieldValue = TextFieldValue(),
    val characterName: TextFieldValue = TextFieldValue(),
    val level: Int = 1,
    val classType: TextFieldValue = TextFieldValue(),
    val armorClass: Int = 0,
    val hitPoint: Int = 0,
    val abilities: Map<Ability, Int> = buildMap { Ability.entries.map { put(it, 10) } },
    val spellSave: Int = 0,
) {

    val isValid: Boolean
        get() {
            if (characterName.text.isEmpty()) return false
            if (playerName.text.isEmpty()) return false
            return true
        }
}
