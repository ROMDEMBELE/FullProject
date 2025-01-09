package ui.spell.search

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.model.Level
import domain.model.spell.Spell
import domain.repository.SpellRepository
import domain.usecase.spell.GetSpellFilterUseCase
import domain.usecase.spell.SaveSpellFilterUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchSpellViewModel(
    private val spellRepository: SpellRepository,
    private val saveSpellFilter: SaveSpellFilterUseCase,
    private val getSpellFilter: GetSpellFilterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SpellListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

            }
        }
    }

    fun toggleSpellIsFavorite(spell: Spell) {

    }

    fun filterByLevel(filter: Level, enable: Boolean) {
        _uiState.update {
            it.copy(filterByLevel = it.filterByLevel.toMutableMap().apply {
                this[filter] = enable
            })
        }
        saveSpellFilter(_uiState.value.filterByLevel)
    }

    fun filterByText(textFieldValue: TextFieldValue) {
        _uiState.update {
            it.copy(textField = textFieldValue)
        }
    }

    fun acknowledgeError() {
        _uiState.update { it.copy(error = null) }
    }

    fun refresh() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _uiState.update { it.copy(isLoading = false) }
                try {

                } catch (e: Exception) {
                    _uiState.update { it.copy(isLoading = true, error = e.message) }
                }
            }
        }
    }

    fun removeFavorite(key: String) {
        TODO("Not yet implemented")
    }

    fun addFavorite(key: String) {
        TODO("Not yet implemented")
    }
}