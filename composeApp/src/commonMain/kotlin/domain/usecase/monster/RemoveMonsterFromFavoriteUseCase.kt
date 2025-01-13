package domain.usecase.monster

import domain.repository.MonsterRepository
import io.github.aakira.napier.Napier

class RemoveMonsterFromFavoriteUseCase(private val monsterRepository: MonsterRepository) {

    private val tag = "RemoveMonsterFromFavoriteUseCase"

    suspend operator fun invoke(key: String) {
        if (monsterRepository.getFavorite(key) != null) {
            monsterRepository.removeFavorite(key)
            Napier.i(tag = tag) { "Monster $key removed from favorites" }
        } else {
            Napier.w(tag = tag) { "Monster $key not found in favorites, nothing to remove" }
        }
    }

}