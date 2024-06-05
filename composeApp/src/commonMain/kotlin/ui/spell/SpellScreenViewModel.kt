package ui.spell

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.Level
import domain.MagicSchool
import domain.Spell
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import repository.SpellRepository

class SpellScreenViewModel(private val spellRepository: SpellRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(SpellListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        searchSpell()
    }

    suspend fun getSpell(index: String): Spell? = spellRepository.getOneSpell(index)

    suspend fun updateFavorite(spell: Spell) {
        if (!spell.isFavorite) {
            spellRepository.addFavorite(spell)
        } else {
            spellRepository.removeFavorite(spell)
        }
        searchSpell()
    }

    fun filterByLevel(filter: Level, enable: Boolean) {
        _uiState.update {
            val updatedList = it.filterByLevel.toMutableList().apply {
                if (enable) add(filter) else remove(filter)
            }
            it.copy(filterByLevel = updatedList)
        }
        searchSpell()
    }

    fun filterByMagicSchool(filter: MagicSchool, enable: Boolean) {
        _uiState.update {
            val updatedList = it.filterByMagicSchool.toMutableList().apply {
                if (enable) add(filter) else remove(filter)
            }
            it.copy(filterByMagicSchool = updatedList)
        }
        searchSpell()
    }

    fun filterByText(textFieldValue: TextFieldValue) {
        _uiState.update {
            it.copy(textField = textFieldValue)
        }
        searchSpell()
    }

    private fun searchSpell() {
        viewModelScope.launch {
            val school = _uiState.value.filterByMagicSchool
            val level = _uiState.value.filterByLevel
            val text = _uiState.value.textField.text

            val spellsByLevel =
                spellRepository.searchSpell(level, school).sortedBy { spell -> spell.level }
                    .filter { spell -> spell.name.contains(text) }.groupBy { spell -> spell.level }
            _uiState.update { it.copy(spellsByLevel = spellsByLevel) }
        }
    }
}