package domain.usecase.monster

import domain.model.monster.Challenge
import domain.model.monster.MonsterReference
import domain.repository.FavoriteRepository
import domain.repository.MonsterRepository
import kotlinx.coroutines.flow.Flow

class FilterMonstersListUseCase(
    private val monsterRepository: MonsterRepository,
    private val favoriteRepository: FavoriteRepository
) {

    suspend operator fun invoke(
        query: String,
        minChallenge: Challenge,
        maxChallenge: Challenge
    ): Flow<List<MonsterReference>> {
        return monsterRepository.search(name = query, min = minChallenge, max = maxChallenge)
    }


}