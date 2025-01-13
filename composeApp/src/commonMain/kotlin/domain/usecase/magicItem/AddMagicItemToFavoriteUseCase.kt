package domain.usecase.magicItem

import domain.repository.MagicItemRepository
import io.github.aakira.napier.Napier

class AddMagicItemToFavoriteUseCase(private val repository: MagicItemRepository) {

    private val tag = "AddMagicItemToFavoriteUseCase"

    suspend operator fun invoke(key: String) {
        if (repository.getFavorite(key) == null) {
            val magicItem = repository.getByKey(key)
            repository.addFavorite(magicItem)
            Napier.i(tag = tag) { "Magic Item added to favorites : ${magicItem.name}" }
        } else {
            Napier.i(tag = tag) { "Magic Item already in favorites : $key, nothing to do" }
        }
    }
}