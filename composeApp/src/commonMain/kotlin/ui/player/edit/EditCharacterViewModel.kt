package ui.player.edit

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import domain.model.Ability
import domain.repository.CharacterRepository
import domain.usecase.EditCharacterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import ui.SharedImage

class EditCharacterViewModel(
    private val characterRepository: CharacterRepository,
    private val createOrUpdateCharacter: EditCharacterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditCharacterUiState())
    val uiState = _uiState.asStateFlow()

    suspend fun loadCharacterToEdit(id: Long) {
        characterRepository.getCharacterById(id).firstOrNull()?.let { character ->
            _uiState.update {
                EditCharacterUiState(
                    id = character.id,
                    level = character.level.level,
                    playerName = TextFieldValue(character.player),
                    characterName = TextFieldValue(character.fullName),
                    armorClass = character.armorClass,
                    hitPoint = character.hitPoint,
                    spellSave = character.spellSavingThrow,
                    abilities = buildMap {
                        put(Ability.STR, character.strength)
                        put(Ability.DEX, character.dexterity)
                        put(Ability.CON, character.constitution)
                        put(Ability.INT, character.intelligence)
                        put(Ability.WIS, character.wisdom)
                        put(Ability.CHA, character.charisma)
                    },
                    profilePicture = character.profilePicture
                )
            }
        } ?: throw NullPointerException("Character id$id not found")
    }

    suspend fun saveCharacter() {
        createOrUpdateCharacter.execute(_uiState.value).firstOrNull()?.let { updatedCharacter ->
            _uiState.update {
                EditCharacterUiState(
                    id = updatedCharacter.id,
                    playerName = TextFieldValue(updatedCharacter.player),
                    characterName = TextFieldValue(updatedCharacter.fullName),
                    armorClass = updatedCharacter.armorClass,
                    hitPoint = updatedCharacter.hitPoint,
                    spellSave = updatedCharacter.spellSavingThrow,
                    abilities = buildMap {
                        put(Ability.STR, updatedCharacter.strength)
                        put(Ability.DEX, updatedCharacter.dexterity)
                        put(Ability.CON, updatedCharacter.constitution)
                        put(Ability.INT, updatedCharacter.intelligence)
                        put(Ability.WIS, updatedCharacter.wisdom)
                        put(Ability.CHA, updatedCharacter.charisma)
                    },
                    profilePicture = updatedCharacter.profilePicture
                )
            }
        } ?: throw Exception("Unable to save character")
    }

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

    suspend fun pickProfilePicture(imageBitmap: SharedImage?) {
        try {

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}