package ui.player

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import domain.model.Ability
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import domain.repository.CharacterRepository

class PlayerViewModel(private val characterRepository: CharacterRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(PlayerCharacterUiState())
    val uiState = _uiState.asStateFlow()

    suspend fun getClasses() = characterRepository.getClasses()

    fun updatePlayerName(textFieldValue: TextFieldValue) {
        _uiState.update {
            it.copy(playerName = textFieldValue)
        }
    }

    fun updateCharacterName(textFieldValue: TextFieldValue) {
        _uiState.update {
            it.copy(characterName = textFieldValue)
        }
    }

    fun updateArmorClass(armorClass: Int) {
        _uiState.update {
            it.copy(armorClass = armorClass)
        }
    }

    fun updateHitPoint(hitPoint: Int) {
        _uiState.update {
            it.copy(hitPoint = hitPoint)
        }
    }

    fun updateSpellSave(spellSave: Int) {
        _uiState.update {
            it.copy(spellSave = spellSave)
        }
    }

    fun updateLevel(level: Int) {
        _uiState.update {
            it.copy(level = level)
        }
    }

    fun updateAbilityScores(ability: Ability, score: Int) {
        _uiState.update {
            val abilities = it.abilities.toMutableMap().apply { put(ability, score) }
            it.copy(abilities = abilities)
        }
    }
}