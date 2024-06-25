package ui.character.edit

import androidx.compose.ui.text.input.TextFieldValue
import domain.model.Ability
import domain.model.character.Background
import domain.model.character.Species

data class EditCharacterUiState(
    val id: Long? = null,
    val playerName: TextFieldValue = TextFieldValue(),
    val characterName: TextFieldValue = TextFieldValue(),
    val characterClass: TextFieldValue = TextFieldValue(),
    val characterBackground: Background? = null,
    val characterSpecies: Species? = null,
    val level: Int = 1,
    val armorClass: Int = 0,
    val hitPoint: Int = 1,
    val spellSave: Int = 0,
    val abilities: Map<Ability, Int> = buildMap { Ability.entries.map { put(it, 10) } },
    val isReady: Boolean = false,
    val backgrounds: Map<Long, Background> = emptyMap(),
    val species: Map<Long, Species> = emptyMap()
) {

    val canBeDeleted: Boolean
        get() {
            return id != null
        }

    val isValid: Boolean
        get() {
            if (characterName.text.isEmpty()) return false
            if (playerName.text.isEmpty()) return false
            if (characterClass.text.isEmpty()) return false
            if (hitPoint < 1) return false
            if (characterSpecies == null) return false
            if (characterBackground == null) return false
            return true
        }
}
