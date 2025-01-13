package ui.spell.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.usecase.spell.AddSpellToFavoritesUseCase
import domain.usecase.spell.GetFavoritesSpellUseCase
import domain.usecase.spell.GetSpellByKeyUseCase
import domain.usecase.spell.RemoveSpellFromFavoritesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SpellDetailsViewModel(
    val key: String,
    private val getFavoritesSpellUseCase: GetFavoritesSpellUseCase,
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
        val spell = getSpellByKeyUseCase(index)
        val isFavorite = getFavoritesSpellUseCase().firstOrNull().orEmpty().any { it.key == index }
        _uiState.update {
            it.copy(
                isReady = true,
                key = spell.key,
                name = spell.name,
                level = spell.level,
                description = spell.description,
                higherLevelDescription = spell.higherLevel,
                school = spell.school,
                isFavorite = isFavorite,
                isAttackRoll = spell.attackRoll,
                isSavingThrow = spell.savingThrowAbility != null,
                savingThrowAbility = spell.savingThrowAbility,
                isConcentration = spell.concentration,
                isRitual = spell.ritual,
                range = spell.range,
                verbal = spell.verbal,
                somatic = spell.somatic,
                material = spell.material,
                cost = spell.cost,
                duration = spell.duration,
                castingTime = spell.castingTime,
                damageType = spell.damageType,
                castingOptions = spell.castingOptions.filter { option ->
                    option.duration != null || option.damageRoll != null || option.targetCount != null
                }
            )
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