package domain.usecase.spell

import domain.model.spell.Spell
import domain.repository.SpellRepository

class GetSpellByKeyUseCase(
    private val spellRepository: SpellRepository
) {

    suspend operator fun invoke(key: String): Spell =
        spellRepository.getFavorite(key) ?: spellRepository.getByKey(key)
}