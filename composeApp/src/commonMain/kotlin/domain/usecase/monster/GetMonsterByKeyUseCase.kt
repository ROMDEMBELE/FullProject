package domain.usecase.monster

import domain.model.monster.Monster
import domain.repository.MonsterRepository

class GetMonsterByKeyUseCase(
    private val monsterRepository: MonsterRepository
) {

    suspend operator fun invoke(key: String): Monster {
        return monsterRepository.getFavorite(key) ?: run { monsterRepository.getByKey(key) }
    }
}