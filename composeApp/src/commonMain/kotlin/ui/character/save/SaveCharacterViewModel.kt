package ui.character.save

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.model.Level
import domain.model.character.CharacterClass
import domain.usecase.character.DeleteCharacterUseCase
import domain.usecase.character.GetCharacterByIdUseCase
import domain.usecase.character.SaveCharacterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SaveCharacterViewModel(
    private val index: String? = null,
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase,
    private val saveCharacterUseCase: SaveCharacterUseCase,
    private val deleteCharacterUseCase: DeleteCharacterUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SaveCharacterUiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            if (index != null) {
                fetchCharacter(index)
            }
        }
    }

    private suspend fun fetchCharacter(index: String) {
        val character = getCharacterByIdUseCase(index)
        _state.update {
            it.copy(
                characterName = TextFieldValue(character.name),
                characterClass = character.characterClass,
                level = character.level,
                armorClass = character.armorClass,
                passivePerception = character.passivePerception,
                intelligence = character.intelligence,
                strength = character.strength,
                dexterity = character.dexterity,
                constitution = character.constitution,
                wisdom = character.wisdom,
                charisma = character.charisma,
                hitPoint = character.hitPoint,
                isReady = true
            )
        }
    }

    fun onNameChange(textFieldValue: TextFieldValue) {
        _state.update {
            it.copy(characterName = textFieldValue)
        }
    }

    fun onArmorClassChange(armorClass: Int) {
        _state.update {
            it.copy(armorClass = armorClass)
        }
    }

    fun onHitPointChange(hitPoint: Int) {
        _state.update {
            it.copy(hitPoint = hitPoint)
        }
    }

    fun onClassChange(characterClass: CharacterClass?) {
        _state.update {
            it.copy(characterClass = characterClass)
        }
    }

    fun onPassivePerceptionChange(passivePerception: Int) {
        _state.update {
            it.copy(passivePerception = passivePerception)
        }
    }

    fun onLevelChange(level: Int) {
        _state.update {
            it.copy(level = Level.fromInt(level))
        }
    }

    fun onIntelligenceChange(value: Int) {
        _state.update {
            it.copy(intelligence = value)
        }
    }

    fun onWisdomChange(value: Int) {
        _state.update {
            it.copy(wisdom = value)
        }
    }

    fun onStrengthChange(value: Int) {
        _state.update {
            it.copy(strength = value)
        }
    }

    fun onDexterityChange(value: Int) {
        _state.update {
            it.copy(dexterity = value)
        }
    }

    fun onConstitutionChange(value: Int) {
        _state.update {
            it.copy(constitution = value)
        }
    }

    fun onCharismaChange(value: Int) {
        _state.update {
            it.copy(charisma = value)
        }
    }

    fun deleteCharacter(onDeleted: () -> Unit) {
        viewModelScope.launch {
            if (index != null) {
                deleteCharacterUseCase(index)
                onDeleted()
            }
        }
    }

    fun saveCharacter(onSaved: () -> Unit) {
        viewModelScope.launch {
        }

    }
}