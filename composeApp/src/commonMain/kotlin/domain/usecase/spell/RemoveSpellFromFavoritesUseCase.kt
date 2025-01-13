package domain.usecase.spell

import domain.repository.SpellRepository
import io.github.aakira.napier.Napier

class RemoveSpellFromFavoritesUseCase(private val spellRepository: SpellRepository) {

    private val tag = "RemoveSpellFromFavoritesUseCase"

    suspend operator fun invoke(key: String) {
        if (spellRepository.getFavorite(key) != null) {
            spellRepository.removeFavorite(key)
            Napier.i(tag = tag) { "Spell $key removed from favorites" }
        } else {
            Napier.w(tag = tag) { "Spell $key not found in favorites, nothing to remove" }
        }
    }
}