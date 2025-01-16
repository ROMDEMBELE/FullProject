package ui.character.save

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.model.Ability
import domain.model.Level
import domain.repository.CharacterRepository
import domain.usecase.character.DeleteCharacterUseCase
import domain.usecase.character.SaveCharacterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SaveCharacterViewModel(
    private val characterRepository: CharacterRepository,
    private val saveCharacter: SaveCharacterUseCase,
    private val deleteCharacter: DeleteCharacterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SaveCharacterUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(isReady = true) }
        }
    }

    suspend fun loadCharacterToEdit(id: Long) {
        characterRepository.getById(id).let { character ->
            _uiState.update {
                it.copy(
                    id = character.id,
                    level = character.level,
                    characterName = TextFieldValue(character.fullName),
                    armorClass = character.armorClass,
                    hitPoint = character.hitPoint,
                    abilities = buildMap {
                        put(Ability.STR, character.strength)
                        put(Ability.DEX, character.dexterity)
                        put(Ability.CON, character.constitution)
                        put(Ability.INT, character.intelligence)
                        put(Ability.WIS, character.wisdom)
                        put(Ability.CHA, character.charisma)
                    },
                )
            }
        } ?: throw NullPointerException("Character id$id not found")
    }

    suspend fun save() {
        val dexterity = _uiState.value.abilities[Ability.DEX] ?: 10
        val constitution = _uiState.value.abilities[Ability.CON] ?: 10
        val intelligence = _uiState.value.abilities[Ability.INT] ?: 10
        val wisdom = _uiState.value.abilities[Ability.WIS] ?: 10
        val strength = _uiState.value.abilities[Ability.STR] ?: 10
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

    fun updateLevel(level: Int) {
        _uiState.update {
            it.copy(level = Level.fromInt(level))
        }
    }

    fun updateAbilityScores(ability: Ability, score: Int) {
        _uiState.update {
            val abilities = it.abilities.toMutableMap().apply { put(ability, score) }
            it.copy(abilities = abilities)
        }
    }

    fun deleteCharacter() {
        viewModelScope.launch {
            try {
                _uiState.value.id?.let {
                    deleteCharacter(it)
                }
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            }
        }
    }

    fun updateCharacterClass(textFieldValue: TextFieldValue) {
    }
}