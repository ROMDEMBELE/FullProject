package domain.repository

import domain.Campaign
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.cancellation.CancellationException

interface CampaignRepository {

    @Throws(NoSuchElementException::class, CancellationException::class)
    suspend fun getById(id: Long): Campaign

    suspend fun getAll(): Flow<List<Campaign>>

    @Throws(NoSuchElementException::class)
    fun createOrUpdate(
        id: Long?,
        name: String,
        description: String,
    )

    @Throws(NoSuchElementException::class, CancellationException::class)
    suspend fun delete(id: Long)
}