package domain.repository

import domain.model.campaign.Encounter
import domain.model.campaign.EncounterFighter
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.cancellation.CancellationException

interface EncounterRepository {

    @Throws(NoSuchElementException::class, CancellationException::class)
    fun getById(id: String): Encounter

    @Throws(NoSuchElementException::class, CancellationException::class)
    fun getAll(): Flow<List<Encounter>>

    @Throws(NoSuchElementException::class, CancellationException::class)
    fun save(
        id: String?,
        campaignId: String,
        title: String,
        description: String,
        turn: Int,
        fighters: List<EncounterFighter>,
        isFinished: Boolean,
    )

    @Throws(NoSuchElementException::class, CancellationException::class)
    suspend fun delete(id: Long)

}