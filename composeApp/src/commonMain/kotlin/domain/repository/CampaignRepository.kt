package domain.repository

import domain.model.campaign.Campaign
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.cancellation.CancellationException

interface CampaignRepository {

    @Throws(
        NoSuchElementException::class,
        CancellationException::class,
        IllegalArgumentException::class
    )
    suspend fun getById(id: String): Campaign

    fun getAll(): Flow<List<Campaign>>

    @Throws(NoSuchElementException::class, CancellationException::class)
    suspend fun createOrUpdate(
        id: String?,
        name: String,
        description: String,
    )

    @Throws(NoSuchElementException::class, CancellationException::class)
    suspend fun delete(id: String)
}