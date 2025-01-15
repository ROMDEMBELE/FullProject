package domain.repository

import domain.model.encounter.Encounter
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.cancellation.CancellationException

interface EncounterRepository {

    @Throws(NoSuchElementException::class, CancellationException::class)
    suspend fun getByCampaignId(campaignId: Long): List<Encounter>

    @Throws(NoSuchElementException::class, CancellationException::class)
    fun getById(id: Long): Encounter

    @Throws(NoSuchElementException::class, CancellationException::class)
    fun getAll(): Flow<List<Encounter>>

    fun save(encounter: Encounter)

    @Throws(NoSuchElementException::class, CancellationException::class)
    suspend fun delete(id: Long)

}