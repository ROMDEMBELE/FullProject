package ui.monster

import androidx.lifecycle.ViewModel
import domain.model.monster.Monster
import domain.repository.MonsterRepository

class MonsterDetailsViewModel(
    private val monsterRepository: MonsterRepository,
) : ViewModel() {

    suspend fun loadMonster(index: String): Monster? = monsterRepository.getMonsterByIndex(index)
}
