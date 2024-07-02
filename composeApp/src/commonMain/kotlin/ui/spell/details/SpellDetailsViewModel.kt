package ui.spell.details

import androidx.lifecycle.ViewModel
import domain.repository.SpellRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SpellDetailsViewModel(private val spellRepository: SpellRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(SpellDetailsUiState())
    val uiState = _uiState.asStateFlow()

    suspend fun fetchSpell(index: String) {
        try {
            val spell = spellRepository.getSpellByIndex(index)
            _uiState.update { it.copy(isReady = true, spell = spell) }
        } catch (e: Exception) {
            _uiState.update { it.copy(isReady = true, error = e.message) }
        }
    }

    fun acknowledgeError() {
        _uiState.update { it.copy(error = null) }
    }
}