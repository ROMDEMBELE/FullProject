package ui.spell

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.Level
import domain.Spell
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import repository.SpellRepository

class SpellScreenViewModel(private val spellRepository: SpellRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(SpellListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        refreshUiState()
    }

    suspend fun getSpell(index: String): Spell? = spellRepository.getSpellByIndex(index)

    fun toggleSpellIsFavorite(spell: Spell) {
        spellRepository.setSpellIsFavorite(spell.index, !spell.isFavorite)
        refreshUiState()
    }

    fun filterByLevel(filter: Level, enable: Boolean) {
        _uiState.update {
            val updatedList = it.filterByLevel.toMutableList().apply {
                if (enable) add(filter) else remove(filter)
            }
            it.copy(filterByLevel = updatedList)
        }
        refreshUiState()
    }

    fun filterByText(textFieldValue: TextFieldValue) {
        _uiState.update {
            it.copy(textField = textFieldValue)
        }
        refreshUiState()
    }

    private fun refreshUiState() {
        viewModelScope.launch {
            val level = _uiState.value.filterByLevel
            val text = _uiState.value.textField.text
            spellRepository.getSpells().collectLatest { list ->
                val favorites = list.filter { spell -> spell.isFavorite }.sortedBy { spell -> spell.level }

                val spellsByLevel = list.asSequence().sortedBy { spell -> spell.level }
                    .filter { spell -> level.isEmpty() || level.contains(spell.level) }
                    .filter { spell -> spell.name.contains(text, true) }
                    .groupBy { spell -> spell.level }
                _uiState.update { it.copy(spellsByLevel = spellsByLevel, favorites = favorites) }
            }

        }
    }
}