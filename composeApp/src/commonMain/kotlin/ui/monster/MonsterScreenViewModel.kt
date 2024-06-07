package ui.monster

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.Challenge
import domain.Monster
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import repository.MonsterRepository

class MonsterScreenViewModel(private val monsterRepository: MonsterRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(MonsterListUiState())
    val uiState: StateFlow<MonsterListUiState> = _uiState.asStateFlow()

    init {
        refreshUiState()
    }

    private fun refreshUiState() {
        viewModelScope.launch {
            monsterRepository.getMonsters().collectLatest { list ->
                val favorites = list.filter { it.isFavorite }
                val text = _uiState.value.textField.text
                val challengeRange =
                    _uiState.value.minChallenge.rating.._uiState.value.maxChallenge.rating
                val monsterByChallenge = list
                    .filter {
                        it.name.contains(
                            text,
                            true
                        ) && it.challenge.rating in challengeRange
                    }
                    .sortedBy { it.challenge }
                    .groupBy { it.challenge }
                _uiState.update {
                    it.copy(monsterByChallenge = monsterByChallenge, favorites = favorites)
                }
            }
        }
    }

    fun setMinChallenge(challenge: Challenge) {
        var max = _uiState.value.maxChallenge
        if (max.rating < challenge.rating) max = challenge.next()
        _uiState.update {
            it.copy(minChallenge = challenge, maxChallenge = max)
        }
        refreshUiState()
    }

    fun setMaxChallenge(challenge: Challenge) {
        var min = _uiState.value.minChallenge
        if (min.rating > challenge.rating) min = challenge.previous()
        _uiState.update {
            it.copy(minChallenge = min, maxChallenge = challenge)
        }
        refreshUiState()
    }

    fun filterByText(textField: TextFieldValue) {
        _uiState.update { it.copy(textField = textField) }
        refreshUiState()
    }

    suspend fun getMonster(index: String): Monster? {
        return monsterRepository.getMonsterByIndex(index)
    }

    fun toggleMonsterFavorite(monster: Monster) {
        monsterRepository.setMonsterIsFavorite(monster.index, !monster.isFavorite)
        refreshUiState()
    }

}