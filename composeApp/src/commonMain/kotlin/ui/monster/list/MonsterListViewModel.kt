package ui.monster.list

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.model.monster.Challenge
import domain.model.monster.Monster
import domain.repository.MonsterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MonsterListViewModel(
    private val monsterRepository: MonsterRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MonsterListUiState())
    val uiState: StateFlow<MonsterListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                monsterRepository.getAll().collectLatest { list ->
                    delay(500)
                    _uiState.update {
                        it.copy(monsterList = list, isReady = true)
                    }
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
    }

    fun setMaxChallenge(challenge: Challenge) {
        var min = _uiState.value.minChallenge
        if (min.rating > challenge.rating) min = challenge.previous()
        _uiState.update {
            it.copy(minChallenge = min, maxChallenge = challenge)
        }
    }

    fun filterByText(textField: TextFieldValue) {
        _uiState.update { it.copy(textField = textField) }
    }

    fun toggleMonsterFavorite(monster: Monster) {
        monsterRepository.setFavorite(monster.index, !monster.isFavorite)
    }

    fun acknowledgeError() {
        _uiState.update { it.copy(error = null) }
    }

    fun refresh() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _uiState.update { it.copy(isReady = false) }
                try {
                    monsterRepository.fetchData()
                } catch (e: Exception) {
                    _uiState.update { it.copy(isReady = true, error = e.message) }
                }
            }
        }
    }

}