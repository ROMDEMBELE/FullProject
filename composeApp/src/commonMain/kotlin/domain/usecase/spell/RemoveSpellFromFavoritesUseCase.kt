package domain.usecase.spell

import domain.repository.SpellRepository
import io.github.aakira.napier.Napier

class RemoveSpellFromFavoritesUseCase(private val spellRepository: SpellRepository) {

    suspend operator fun invoke(key: String) {
        if (spellRepository.getFavorite(key) != null) {
            spellRepository.removeFavorite(key)
            Napier.d("Spell removed from favorites")
        } else {
            Napier.w { "Spell not in favorites" }
        }
    }
}