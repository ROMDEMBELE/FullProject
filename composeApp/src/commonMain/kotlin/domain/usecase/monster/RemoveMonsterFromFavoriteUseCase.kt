package domain.usecase.monster

import domain.repository.MonsterRepository
import io.github.aakira.napier.Napier

class RemoveMonsterFromFavoriteUseCase(private val monsterRepository: MonsterRepository) {

    suspend operator fun invoke(key: String) {
        // Check if the monster is in favorites
        val favorite = monsterRepository.getFavorite(key)
        if (favorite == null) {
            Napier.d("Monster not in favorites")
        } else {
            monsterRepository.removeFavorite(favorite.key)

            Napier.d("Monster added to favorites")
        }
    }

}