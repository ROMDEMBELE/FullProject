package domain.usecase.monster

import domain.model.monster.Monster
import domain.repository.FavoriteRepository
import domain.repository.MonsterRepository

class GetMonsterByKeyUseCase(
    private val favoriteRepository: FavoriteRepository,
    private val monsterRepository: MonsterRepository
) {

    suspend operator fun invoke(key: String): Monster {
        val monster = monsterRepository.getByKey(key)
        return monster
    }
}