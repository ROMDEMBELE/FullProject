package data.repository

import data.api.SpellApi
import domain.model.Level
import domain.model.spell.Spell
import domain.repository.SpellRepository
import kotlinx.coroutines.flow.Flow

class SpellRepositoryImpl(
    private val spellApi: SpellApi
) : SpellRepository {

    override suspend fun getBySlug(slug: String): Spell {
        TODO("Not yet implemented")

    }

    override suspend fun search(
        name: String,
        min: Level,
        max: Level
    ): Flow<List<Spell>> {
        TODO("Not yet implemented")
    }

}