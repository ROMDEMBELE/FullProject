package domain.repository

import domain.model.character.Character
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.cancellation.CancellationException

interface CharacterRepository {

    @Throws(NoSuchElementException::class, CancellationException::class)
    suspend fun getById(id: Long): Character

    @Throws(NoSuchElementException::class, CancellationException::class)
    suspend fun getByCampaignId(campaignId: Long): List<Character>

    @Throws(NoSuchElementException::class, CancellationException::class)
    suspend fun delete(id: Long)

    fun getAll(): Flow<List<Character>>

    suspend fun save(character: Character)
}