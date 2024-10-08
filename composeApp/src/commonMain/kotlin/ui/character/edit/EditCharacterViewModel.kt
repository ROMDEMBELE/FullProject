package ui.character.edit

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.model.Ability
import domain.model.Level
import domain.model.character.Background
import domain.model.character.Species
import domain.repository.BackgroundRepository
import domain.repository.CharacterRepository
import domain.repository.SpeciesRepository
import domain.usecase.character.DeleteCharacterUseCase
import domain.usecase.character.SaveCharacterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditCharacterViewModel(
    private val characterRepository: CharacterRepository,
    private val speciesRepository: SpeciesRepository,
    private val backgroundRepository: BackgroundRepository,
    private val saveCharacter: SaveCharacterUseCase,
    private val deleteCharacter: DeleteCharacterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditCharacterUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            loadBackground()
            loadSpecies()
            _uiState.update { it.copy(isReady = true) }
        }
    }

    private suspend fun loadBackground() {
        backgroundRepository.getListOfBackground().firstOrNull()?.let { backgrounds ->
            _uiState.update {
                it.copy(backgrounds = backgrounds.associateBy(Background::id))
            }
        }
    }

    private suspend fun loadSpecies() {
        speciesRepository.getList().firstOrNull()?.let { species ->
            _uiState.update {
                it.copy(species = species.associateBy(Species::id))
            }
        }
    }

    suspend fun loadCharacterToEdit(id: Long) {
        characterRepository.getById(id).firstOrNull()?.let { character ->
            _uiState.update {
                it.copy(
                    id = character.id,
                    level = character.level,
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
                    characterClass = TextFieldValue(character.characterClass),
                    characterBackground = it.backgrounds[character.backgroundId],
                    characterSpecies = it.species[character.speciesId]
                )
            }
        } ?: throw NullPointerException("Character id$id not found")
    }

    suspend fun save() {
        val (id, playerName, characterName, characterClass, characterBackground, characterSpecies,
            level, armorClass, hitPoint, spellSave) = _uiState.value
        val dexterity = _uiState.value.abilities[Ability.DEX] ?: 10
        val constitution = _uiState.value.abilities[Ability.CON] ?: 10
        val intelligence = _uiState.value.abilities[Ability.INT] ?: 10
        val wisdom = _uiState.value.abilities[Ability.WIS] ?: 10
        val strength = _uiState.value.abilities[Ability.STR] ?: 10
        saveCharacter(
            id,
            playerName,
            characterName,
            level,
            armorClass,
            hitPoint,
            spellSave,
            dexterity,
            constitution,
            intelligence,
            wisdom,
            strength,
            spellSave,
            characterSpecies ?: error("Species not selected"),
            characterBackground ?: error("Background not selected"),
            characterClass,
        )?.let { updatedCharacter ->
            _uiState.update {
                it.copy(
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
                    characterBackground = it.backgrounds[updatedCharacter.backgroundId],
                    characterSpecies = it.species[updatedCharacter.speciesId]
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

    fun updateCharacterBackground(background: Background) {
        _uiState.update {
            it.copy(characterBackground = background)
        }
    }

    fun updateCharacterSpecies(species: Species) {
        _uiState.update {
            it.copy(characterSpecies = species)
        }
    }

    fun updateCharacterClass(textFieldValue: TextFieldValue) {
        _uiState.update {
            it.copy(characterClass = textFieldValue)
        }
    }
}