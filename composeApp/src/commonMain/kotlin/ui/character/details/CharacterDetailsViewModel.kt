package ui.character.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.model.Ability
import domain.usecase.character.DeleteCharacterUseCase
import domain.usecase.character.GetCharacterByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterDetailsViewModel(
    private val index: String,
    private val getCharacterById: GetCharacterByIdUseCase,
    private val deleteCharacter: DeleteCharacterUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterDetailsUiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            fetchCharacter(index)
        }
    }

    private suspend fun fetchCharacter(index: String) {
        val character = getCharacterById(index)
        _state.update {
            it.copy(
                isReady = true,
                characterName = character.name,
                armorClass = character.armorClass,
                hitPoint = character.hitPoint,
                characterLevel = character.level,
                abilities = mapOf(
                    Ability.STR to character.strength,
                    Ability.DEX to character.dexterity,
                    Ability.CON to character.constitution,
                    Ability.INT to character.intelligence,
                    Ability.WIS to character.wisdom,
                    Ability.CHA to character.charisma
                ),
                perceptionPassive = character.passivePerception,
            )
        }

    }

    fun deleteCharacter(onDeleted: () -> Unit) {
        viewModelScope.launch {
            deleteCharacter(index)
            onDeleted()
        }
    }

}