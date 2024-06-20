package ui.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.repository.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharacterViewModel(private val repository: CharacterRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(CharacterListUiState(isLoading = true))
    val uiState: StateFlow<CharacterListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getListOfCharacters().collectLatest {
                _uiState.value = CharacterListUiState(characters = it, isLoading = false)
            }
        }
    }
}