package domain.usecase.monster

import domain.model.monster.Monster
import domain.repository.MonsterRepository
import kotlinx.coroutines.flow.Flow

class GetFavoritesMonsterUseCase(private val monsterRepository: MonsterRepository) {

    suspend operator fun invoke(): Flow<List<Monster>> {
        return monsterRepository.getFavorites()
    }
}