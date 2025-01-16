package domain.usecase.spell

import domain.model.spell.Spell
import domain.repository.SpellRepository
import kotlinx.coroutines.flow.Flow

class GetFavoritesSpellUseCase(private val spellRepository: SpellRepository) {

    suspend operator fun invoke(): Flow<List<Spell>> = spellRepository.getFavorites()
}