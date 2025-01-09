package ui.magicItem.search

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.model.magicItem.MagicItem
import domain.model.magicItem.Rarity
import domain.repository.MagicItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchMagicItemViewModel(private val repository: MagicItemRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchMagicItemUiState())
    val uiState: StateFlow<SearchMagicItemUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

            }
        }
    }

    fun filterByText(textField: TextFieldValue) {
        _uiState.update { it.copy(textField = textField) }
    }

    fun addRarityToFilter(rarity: Rarity) {
        _uiState.update { it.copy(rarityFilter = it.rarityFilter + rarity) }
    }

    fun removeRarityFromFilter(rarity: Rarity) {
        _uiState.update { it.copy(rarityFilter = it.rarityFilter - rarity) }
    }

    fun addToFavorite(item: MagicItem) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
            }
        }
    }

    fun removeFromFavorite(item: MagicItem) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
            }
        }
    }

    fun acknowledgeError() {
        _uiState.update { it.copy(error = null) }
    }

}