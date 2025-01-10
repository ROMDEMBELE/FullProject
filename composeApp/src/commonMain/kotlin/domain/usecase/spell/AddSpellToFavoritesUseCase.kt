package domain.usecase.spell

import domain.repository.SpellRepository

class AddSpellToFavoritesUseCase(private val spellRepository: SpellRepository) {

    suspend operator fun invoke(key: String) {
        // check if spell already in favorites
        if (spellRepository.getFavorite(key) == null) {
            val spell = spellRepository.getByKey(key)
            spellRepository.addFavorite(spell)
        }
        // spell already in favorites
    }
}