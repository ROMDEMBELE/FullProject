package ui.monster.search

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.model.monster.Challenge
import domain.model.monster.Monster
import domain.usecase.monster.AddMonsterToFavoriteUseCase
import domain.usecase.monster.GetFavoritesMonsterUseCase
import domain.usecase.monster.RemoveMonsterFromFavoriteUseCase
import domain.usecase.monster.SearchMonstersUseCase
import domain.usecase.settings.GetChallengeFilterUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * View model for the search monster screen.
 */
class SearchMonsterViewModel(
    private val searchMonstersUseCase: SearchMonstersUseCase,
    private val addToFavoriteUseCase: AddMonsterToFavoriteUseCase,
    private val removeFromFavoriteUseCase: RemoveMonsterFromFavoriteUseCase,
    private val getFavoritesMonsterUseCase: GetFavoritesMonsterUseCase,
    private val challengeFilter: GetChallengeFilterUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SearchMonsterUiState())
    val state: StateFlow<SearchMonsterUiState> = _state.asStateFlow()

    private var searchJob: Job? = null

    init {
        fetchChallengeFilter()

        fetchFavorites()
    }

    private fun fetchChallengeFilter() {
        viewModelScope.launch {
            _state.update { it.copy(filterChallengeRange = challengeFilter()) }
        }
    }

    private fun fetchMonsters(text: TextFieldValue, min: Challenge, max: Challenge) {
        searchJob = viewModelScope.launch {
            searchMonstersUseCase(text, min, max).collect { results ->
                _state.update {
                    it.copy(
                        searchResult = results.map { monster -> monster.toSearchMonsterItem() },
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun fetchFavorites() {
        viewModelScope.launch {
            getFavoritesMonsterUseCase().collect { favorites ->
                _state.update {
                    it.copy(
                        favorites = favorites.map { monster -> monster.toSearchMonsterItem() },
                        searchResult = it.searchResult.map { item ->
                            item.copy(isFavorite = favorites.any { favorite -> favorite.key == item.key })
                        }
                    )
                }
            }
        }
    }

    private fun Monster.toSearchMonsterItem(): SearchMonsterItem =
        SearchMonsterItem(
            key = key,
            name = name,
            isFavorite = isFavorite,
            challenge = challenge,
            type = type
        )

    private fun searchMonsters() {
        searchJob?.cancel()
        val min = state.value.minChallenge
        val max = state.value.maxChallenge
        val text = state.value.searchTextField
        if (text.text.isNotBlank()) {
            _state.update { it.copy(searchResult = emptyList(), isLoading = true) }
            fetchMonsters(text, min, max)
        } else {
            _state.update { it.copy(isLoading = false) }
        }
    }

    fun cancelSearch() {
        searchJob?.cancel()
        _state.update { it.copy(isLoading = false) }
    }

    fun setChallengeRange(range: ClosedFloatingPointRange<Float>) {
        viewModelScope.launch {
            _state.update {
                it.copy(filterChallengeRange = range)
            }
            delay(500)
            searchMonsters()
        }
    }

    fun onSearchTextChange(textField: TextFieldValue) {
        viewModelScope.launch {
            _state.update { it.copy(searchTextField = textField) }
            delay(500)
            searchMonsters()
        }
    }

    fun addFavorite(slug: String) {
        viewModelScope.launch {
            addToFavoriteUseCase(slug)
        }
    }

    fun removeFavorite(slug: String) {
        viewModelScope.launch {
            removeFromFavoriteUseCase(slug)
        }
    }

}