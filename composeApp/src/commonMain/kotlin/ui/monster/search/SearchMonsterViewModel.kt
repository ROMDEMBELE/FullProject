package ui.monster.search

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.model.monster.Monster
import domain.usecase.monster.AddMonsterToFavoriteUseCase
import domain.usecase.monster.ChallengeFilterUseCase
import domain.usecase.monster.GetFavoritesMonsterUseCase
import domain.usecase.monster.RemoveMonsterFromFavoriteUseCase
import domain.usecase.monster.SearchMonstersUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchMonsterViewModel(
    private val searchMonstersUseCase: SearchMonstersUseCase,
    private val addToFavoriteUseCase: AddMonsterToFavoriteUseCase,
    private val removeFromFavoriteUseCase: RemoveMonsterFromFavoriteUseCase,
    private val getFavoritesMonsterUseCase: GetFavoritesMonsterUseCase,
    private val challengeFilter: ChallengeFilterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchMonsterUiState())
    val uiState: StateFlow<SearchMonsterUiState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    private fun Monster.toSearchMonsterItem(): SearchMonsterItem = SearchMonsterItem(
        slug = key,
        name = name,
        isFavorite = isFavorite,
        challenge = challenge
    )

    init {
        _uiState.update {
            // Load the challenge filter from preferences
            it.copy(filterChallengeRange = challengeFilter.get())
        }

        viewModelScope.launch {
            getFavoritesMonsterUseCase().collect { favorites ->
                _uiState.update {
                    it.copy(favoriteMonster = favorites.map { monster -> monster.toSearchMonsterItem() })
                }
            }
        }
    }

    private fun searchMonsters() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            // Clean the list of monsters
            _uiState.update { it.copy(searchResult = emptyList(), isLoading = true) }

            val min = _uiState.value.minChallenge
            val max = _uiState.value.maxChallenge
            val text = _uiState.value.textField

            // Start the search
            searchMonstersUseCase(text, min, max).collect { monster ->
                delay(500)
                _uiState.update {
                    it.copy(
                        searchResult = it.searchResult + monster.map { monster -> monster.toSearchMonsterItem() },
                        isLoading = false,
                    )
                }
            }
        }
    }

    fun toggleFavorites() {
        _uiState.update {
            it.copy(showFavorites = !it.showFavorites)
        }
        searchJob?.cancel()
    }

    fun setChallengeRange(range: ClosedFloatingPointRange<Float>) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(filterChallengeRange = range)
            }
            challengeFilter.save(range)
            delay(5000)
            searchMonsters()
        }
    }

    fun filterByText(textField: TextFieldValue) {
        viewModelScope.launch {
            _uiState.update { it.copy(textField = textField) }
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