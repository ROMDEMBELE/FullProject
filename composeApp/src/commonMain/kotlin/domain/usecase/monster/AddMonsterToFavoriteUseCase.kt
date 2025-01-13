package domain.usecase.monster

import domain.repository.MonsterRepository
import io.github.aakira.napier.Napier

class AddMonsterToFavoriteUseCase(private val monsterRepository: MonsterRepository) {

    private val tag = "AddMonsterToFavoriteUseCase"

    suspend operator fun invoke(key: String) {
        // Check if the monster is already in favorites
        if (monsterRepository.getFavorite(key) == null) {
            val monster = monsterRepository.getByKey(key)
            monsterRepository.addFavorite(monster)
            Napier.i(tag = tag) { "Monster $key added to favorites" }
        } else {
            Napier.w(tag = tag) { "Monster $key is already in favorites" }
        }
    }

}