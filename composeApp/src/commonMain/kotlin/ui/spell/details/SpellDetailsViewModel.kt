package ui.spell.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.usecase.spell.AddSpellToFavoritesUseCase
import domain.usecase.spell.GetSpellByKeyUseCase
import domain.usecase.spell.RemoveSpellFromFavoritesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SpellDetailsViewModel(
    val key: String,
    private val getSpellByKeyUseCase: GetSpellByKeyUseCase,
    private val addSpellToFavoritesUseCase: AddSpellToFavoritesUseCase,
    private val removeSpellFromFavoritesUseCase: RemoveSpellFromFavoritesUseCase
) :
    ViewModel() {

    private val _uiState = MutableStateFlow(SpellDetailsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            fetchSpell(key)
        }
    }

    private suspend fun fetchSpell(index: String) {
        getSpellByKeyUseCase(index).let {
            _uiState.update {
                it.copy(
                    isReady = true,
                    key = it.key,
                    name = it.name,
                    level = it.level,
                    description = it.description,
                    higherLevelDescription = it.higherLevelDescription,
                    school = it.school,
                    isFavorite = it.isFavorite,
                    isAttackRoll = it.isAttackRoll,
                    isSavingThrow = it.isSavingThrow,
                    savingThrowAbility = it.savingThrowAbility,
                    isConcentration = it.isConcentration,
                    isRitual = it.isRitual,
                    range = it.range,
                    verbal = it.verbal,
                    somatic = it.somatic,
                    material = it.material,
                    cost = it.cost,
                    duration = it.duration,
                    castingTime = it.castingTime,
                    damageType = it.damageType,
                    castingOptions = it.castingOptions
                )
            }
        }
    }

    fun addToFavorites() {
        viewModelScope.launch {
            addSpellToFavoritesUseCase(key)
            _uiState.update { it.copy(isFavorite = true) }
        }
    }

    fun removeFromFavorites() {
        viewModelScope.launch {
            removeSpellFromFavoritesUseCase(key)
            _uiState.update { it.copy(isFavorite = false) }
        }
    }

}