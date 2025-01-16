package ui.spell.search

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.model.Level
import domain.model.spell.Spell
import domain.usecase.spell.AddSpellToFavoritesUseCase
import domain.usecase.spell.GetFavoritesSpellUseCase
import domain.usecase.settings.GetLevelFilterUseCase
import domain.usecase.spell.RemoveSpellFromFavoritesUseCase
import domain.usecase.spell.SearchSpellUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchSpellViewModel(
    private val getFavoritesUseCase: GetFavoritesSpellUseCase,
    private val searchSpellUseCase: SearchSpellUseCase,
    private val levelFilterUseCase: GetLevelFilterUseCase,
    private val addSpellToFavoritesUseCase: AddSpellToFavoritesUseCase,
    private val removeSpellFromFavoritesUseCase: RemoveSpellFromFavoritesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(SearchSpellUiState())
    val state = _state.asStateFlow()

    private var searchJob: Job? = null

    private fun Spell.toSearchSpellItem() = SearchSpellItem(
        key = key,
        name = name,
        level = level,
        isFavorite = isFavorite,
    )

    init {
        fetchFavorites()

        fetchSpellFilter()
    }

    private fun fetchFavorites() {
        viewModelScope.launch {
            getFavoritesUseCase().collect { favorites ->
                _state.update { state ->
                    state.copy(
                        favorites = favorites.map { spell -> spell.toSearchSpellItem() },
                        searchResult = state.searchResult.map { spell ->
                            spell.copy(isFavorite = favorites.any { it.key == spell.key })
                        }
                    )
                }
            }
        }
    }

    private fun fetchSpellFilter() {
        viewModelScope.launch {
            val filter = levelFilterUseCase.get()
            _state.update { it.copy(filterLevelRange = filter) }
        }
    }

    private fun fetchSpell(text: TextFieldValue, minLevel: Level, maxLevel: Level) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            searchSpellUseCase(text, minLevel, maxLevel).collect { result ->
                _state.update {
                    it.copy(
                        searchResult = result.map { spell -> spell.toSearchSpellItem() },
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun searchSpells() {
        searchJob?.cancel()
        val min = state.value.minLevel
        val max = state.value.maxLevel
        val text = state.value.searchTextField
        if (text.text.isNotBlank()) {
            _state.update { it.copy(searchResult = emptyList(), isLoading = true) }
            fetchSpell(text, min, max)
        } else {
            _state.update { it.copy(isLoading = false) }
        }
    }

    fun cancelSearch() {
        searchJob?.cancel()
    }

    fun filterByText(textFieldValue: TextFieldValue) {
        viewModelScope.launch {
            _state.update { it.copy(searchTextField = textFieldValue) }
            delay(500)
            searchSpells()
        }
    }

    fun removeFavorite(key: String) {
        viewModelScope.launch {
            removeSpellFromFavoritesUseCase(key)
        }
    }

    fun addFavorite(key: String) {
        viewModelScope.launch {
            addSpellToFavoritesUseCase(key)
        }
    }

    fun setLevelRange(range: ClosedFloatingPointRange<Float>) {
        viewModelScope.launch {
            _state.update {
                it.copy(filterLevelRange = range)
            }
            val min = _state.value.minLevel
            val max = _state.value.maxLevel
            levelFilterUseCase.save(min, max)
            delay(500)
            searchSpells()
        }
    }
}