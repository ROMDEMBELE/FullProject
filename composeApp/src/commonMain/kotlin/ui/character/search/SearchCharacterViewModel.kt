package ui.character.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchCharacterViewModel(
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchCharacterUiState())
    val uiState: StateFlow<SearchCharacterUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {

        }
    }
}