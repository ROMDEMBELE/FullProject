package ui.monster.list

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.usecase.AddToFavoriteUseCase
import domain.usecase.RemoveFromFavoriteUseCase
import domain.usecase.monster.ChallengeFilterUseCase
import domain.usecase.monster.FilterMonstersListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MonsterListViewModel(
    private val getFilteredMonsterListUseCase: FilterMonstersListUseCase,
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val removeFromFavoriteUseCase: RemoveFromFavoriteUseCase,
    private val challengeFilter: ChallengeFilterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MonsterListUiState())
    val uiState: StateFlow<MonsterListUiState> = _uiState.asStateFlow()

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val min = _uiState.value.minChallenge
                val max = _uiState.value.maxChallenge
                val text = _uiState.value.textField.text
                getFilteredMonsterListUseCase(text, min, max).collect { monster ->
                    delay(500)
                    _uiState.update {
                        it.copy(
                            monsterList = monster.map { monster ->
                                MonsterListItem(
                                    slug = monster.key,
                                    name = monster.name,
                                    isFavorite = monster.isFavorite,
                                    challenge = monster.challenge
                                )
                            },
                            isReady = true,
                            filterChallengeRange = challengeFilter.get()
                        )
                    }
                }
            }
        }
    }

    fun setChallengeRange(range: ClosedFloatingPointRange<Float>) {
        _uiState.update {
            it.copy(filterChallengeRange = range)
        }
        challengeFilter.save(range)
    }

    fun filterByText(textField: TextFieldValue) {
        _uiState.update { it.copy(textField = textField) }
    }

    fun addFavorite(slug: String) {
        addToFavoriteUseCase(slug)
    }

    fun removeFavorite(slug: String) {
        removeFromFavoriteUseCase(slug)
    }

    fun acknowledgeError() {
        _uiState.update { it.copy(error = null) }
    }

}