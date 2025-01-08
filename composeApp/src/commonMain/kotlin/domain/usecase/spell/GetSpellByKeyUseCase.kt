package domain.usecase.spell

import domain.model.spell.Spell
import domain.repository.FavoriteRepository
import domain.repository.SpellRepository

class GetSpellByKeyUseCase(
    private val spellRepository: SpellRepository, private val favoriteRepository: FavoriteRepository
) {

    suspend operator fun invoke(key: String): Spell {
        val spell = spellRepository.getBySlug(key)
        spell.isFavorite = favoriteRepository.isFavorite(key)
        return spell
    }
}