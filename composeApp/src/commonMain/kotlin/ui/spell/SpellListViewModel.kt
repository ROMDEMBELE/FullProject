package ui.spell

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.model.Level
import domain.model.spell.Spell
import domain.repository.SpellRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SpellListViewModel(private val spellRepository: SpellRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(SpellListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            spellRepository.getListOfSpells().collectLatest { list ->
                delay(500)
                _uiState.update { it.copy(spellByLevel = list, isReady = true) }
            }
        }
    }

    fun toggleSpellIsFavorite(spell: Spell) {
        spellRepository.setFavorite(spell.index, !spell.isFavorite)
    }

    fun filterByLevel(filter: Level, enable: Boolean) {
        _uiState.update {
            val updatedList = it.filterByLevel.toMutableList().apply {
                if (enable) add(filter) else remove(filter)
            }
            it.copy(filterByLevel = updatedList)
        }
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
            _uiState.update { it.copy(isReady = false) }
            try {
                spellRepository.fetchSpellDatabase()
            } catch (e: Exception) {
                _uiState.update { it.copy(isReady = true, error = e.message) }
            }
        }
    }
}