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

class SpellScreenViewModel(private val spellRepository: SpellRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(
        SpellListUiState(
            spellsByLevel = mapOf(),
            filterByLevel = buildMap {
                Level.entries.subList(0, 10).forEach {
                    put(it, false)
                }
            },
            filterByMagicSchool = buildMap {
                MagicSchool.entries.forEach {
                    put(it, false)
                }
            },
            textField = TextFieldValue()
        )
    )

    val uiState = _uiState.asStateFlow()

    init {
        searchSpell()
    }

    suspend fun getSpell(index: String): Spell? = spellRepository.getOneSpell(index)

    suspend fun updateFavorite(spell: Spell) {
        if (!spell.isFavorite) {
            spellRepository.addSpellToFavorite(spell)
        } else {
            spellRepository.removeSpellFromFavorite(spell)
        }
    }

    fun filterLevel(level: Level, checked: Boolean) {
        _uiState.update {
            it.copy(filterByLevel = it.filterByLevel.plus(level to checked))
        }
        searchSpell()
    }

    fun filterMagicSchool(school: MagicSchool, checked: Boolean) {
        _uiState.update {
            it.copy(filterByMagicSchool = it.filterByMagicSchool.plus(school to checked))
        }
        searchSpell()
    }

    private fun searchSpell() {
        viewModelScope.launch {
            val school = _uiState.value.filterByMagicSchool.filterValues { it }.keys
            val level = _uiState.value.filterByLevel.filterValues { it }.keys
            val text = _uiState.value.textField.text

            val spellsByLevel = spellRepository.searchSpell(level.toList(), school.toList())
                .sortedBy { spell -> spell.level }
                .filter { spell -> spell.name.contains(text) }
                .groupBy { spell -> spell.level }
            _uiState.update { it.copy(spellsByLevel = spellsByLevel) }
        }
    }
}