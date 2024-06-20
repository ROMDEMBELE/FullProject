package ui.player.edit

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import domain.model.Ability
import domain.repository.CharacterRepository
import domain.usecase.EditCharacterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update

class EditCharacterViewModel(
    private val characterRepository: CharacterRepository,
    private val createOrUpdateCharacter: EditCharacterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditCharacterUiState())
    val uiState = _uiState.asStateFlow()

    suspend fun loadCharacter(id: Long) {
        characterRepository.getCharacterById(id).filterNotNull().collectLatest { character ->
            _uiState.update {
                EditCharacterUiState(id = character.id,
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
                    })
            }
        }
    }

    suspend fun saveCharacter() {
        createOrUpdateCharacter.execute(_uiState.value).filterNotNull()
            .collectLatest { character ->
                EditCharacterUiState(id = character.id,
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
                    })
            }
    }

    //suspend fun getClasses() = characterRepository.getClasses()

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