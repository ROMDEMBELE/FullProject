package ui.character

import androidx.compose.ui.text.input.TextFieldValue
import domain.Ability
import domain.CharacterClass
import domain.Level

data class CharacterUiState(
    val name: TextFieldValue = TextFieldValue(),
    val age: TextFieldValue = TextFieldValue(),
    // val race: TextFieldValue = TextFieldValue(),
    val characterClass: CharacterClass? = null,
    val level: Level = Level.LEVEL_0,
    //val subclass: TextFieldValue = TextFieldValue(),
    val abilities: Map<Ability, Int> = buildMap { Ability.entries.map { put(it, 10) } },
    val features: List<String> = emptyList()
) {

    val isValid: Boolean
        get() {
            if (name.text.length < 3) return false
            if (age.text.isEmpty()) return false
            if (characterClass == null) return false
            return true
        }
}
