package ui.monster.list

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.model.monster.Monster
import domain.repository.MonsterRepository
import domain.usecase.monster.ChallengeFilterUseCase
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
    private val challengeFilter: ChallengeFilterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MonsterListUiState())
    val uiState: StateFlow<MonsterListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                monsterRepository.getAll().collectLatest { list ->
                    delay(500)
                    _uiState.update {
                        it.copy(
                            monsterList = list,
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