package ui.character

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import domain.Ability
import domain.DndClass
import domain.Level
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import repository.CharacterRepository

class CharacterViewModel(private val characterRepository: CharacterRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(CharacterUiState())
    val uiState = _uiState.asStateFlow()

    suspend fun getClasses() = characterRepository.getClasses()

    fun updateName(textFieldValue: TextFieldValue) {
        _uiState.update {
            it.copy(name = textFieldValue)
        }
    }

    fun updateAge(textFieldValue: TextFieldValue) {
        _uiState.update {
            it.copy(age = textFieldValue)
        }
    }

    fun updateClass(selectedClass: DndClass?) {
        _uiState.update {
            it.copy(characterClass = selectedClass)
        }
    }

    fun updateAbilityScores(ability: Ability, score: Int) {
        _uiState.update {
            it.copy(
                abilities = it.abilities.toMutableMap().apply {
                    put(ability, score)
                })
        }
    }

    fun saveCharacter() {
        characterRepository.saveCharacter(
            name = _uiState.value.name.text,
            age = _uiState.value.age.text.toInt(),
            level = _uiState.value.level,
            characterClass = _uiState.value.characterClass!!,
            abilities = _uiState.value.abilities
        )
        _uiState.update { CharacterUiState() }
    }

    fun updateLevel(level: Int) {
        _uiState.update {
            it.copy(level = Level.fromInt(level))
        }
    }
}