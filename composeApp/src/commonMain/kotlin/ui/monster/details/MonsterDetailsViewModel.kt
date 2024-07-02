package ui.monster.details

import androidx.lifecycle.ViewModel
import domain.repository.MonsterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MonsterDetailsViewModel(
    private val monsterRepository: MonsterRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MonsterDetailsUiState())
    val uiState = _uiState.asStateFlow()

    suspend fun fetchMonster(index: String) {
        try {
            val monster = monsterRepository.getMonsterByIndex(index)
            _uiState.update { it.copy(isReady = true, monster = monster) }
        } catch (e: Exception) {
            _uiState.update { it.copy(isReady = true, error = e.message) }
        }
    }
}
