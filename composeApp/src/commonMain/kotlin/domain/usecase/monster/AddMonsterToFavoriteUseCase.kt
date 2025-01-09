package domain.usecase.monster

import domain.repository.MonsterRepository
import io.github.aakira.napier.Napier

class AddMonsterToFavoriteUseCase(private val monsterRepository: MonsterRepository) {

    suspend operator fun invoke(key: String) {
        // Check if the monster is already in favorites
        if (monsterRepository.getFavorite(key) != null) {
            Napier.d("Monster already in favorites")
        } else {
            val monster = monsterRepository.getByKey(key)

            monsterRepository.addFavorite(monster)

            Napier.d("Monster added to favorites")
        }
    }

}