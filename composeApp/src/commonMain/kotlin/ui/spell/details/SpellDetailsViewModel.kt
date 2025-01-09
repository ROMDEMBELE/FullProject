package ui.spell.details

import androidx.lifecycle.ViewModel
import domain.repository.SpellRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SpellDetailsViewModel(val index: String, private val spellRepository: SpellRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(SpellDetailsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchSpell(index)
    }

    fun fetchSpell(index: String) {

    }

    fun acknowledgeError() {
        _uiState.update { it.copy(error = null) }
    }
}