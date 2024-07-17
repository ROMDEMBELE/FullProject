package ui.magicItem.details

import androidx.lifecycle.ViewModel
import domain.repository.MagicItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MagicItemDetailsViewModel(
    private val repository: MagicItemRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MagicItemDetailsUiState())
    val uiState = _uiState.asStateFlow()

    fun fetchMagicItem(index: String) {
        repository.getByIndex(index)?.let { magicItem ->
            _uiState.update { it.copy(magicItem = magicItem, isReady = true) }
        }
    }
}