package domain.usecase.magicItem

import domain.repository.MagicItemRepository
import io.github.aakira.napier.Napier

class RemoveMagicItemFromFavoriteUseCase(private val repository: MagicItemRepository) {

    private val tag = "RemoveMagicItemFromFavoriteUseCase"

    suspend operator fun invoke(key: String) {
        val magicItem = repository.getFavorite(key)
        if (magicItem != null) {
            repository.removeFavorite(key)
            Napier.i(tag = tag) { "Magic Item removed from favorites : ${magicItem.name}" }
        } else {
            Napier.i(tag = tag) { "Magic Item not in favorites : $key, nothing to remove" }
        }
    }
}