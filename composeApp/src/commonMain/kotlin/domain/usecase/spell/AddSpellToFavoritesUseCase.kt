package domain.usecase.spell

import domain.repository.SpellRepository
import io.github.aakira.napier.Napier

class AddSpellToFavoritesUseCase(private val spellRepository: SpellRepository) {

    private val tag = "AddSpellToFavoritesUseCase"

    suspend operator fun invoke(key: String) {
        if (spellRepository.getFavorite(key) == null) {
            val spell = spellRepository.getByKey(key)
            spellRepository.addFavorite(spell)
            Napier.i(tag = tag) { "Spell $key added to favorites" }
        } else {
            Napier.w(tag = tag) { "Spell $key is already in favorites, nothing to add" }
        }
    }
}