package data.api

import data.api.dto.SearchResultDto
import io.ktor.client.plugins.ServerResponseException
import kotlinx.serialization.json.JsonObject
import kotlin.coroutines.cancellation.CancellationException

interface MonsterApi {

    @Throws(ServerResponseException::class, CancellationException::class)
    suspend fun getByKey(key: String): SearchResultDto<JsonObject>

    @Throws(ServerResponseException::class, CancellationException::class)
    suspend fun getByChallenge(challenge: Double): SearchResultDto<JsonObject>

    @Throws(ServerResponseException::class, CancellationException::class)
    suspend fun search(
        query: String,
        minChallenge: Double,
        maxChallenge: Double
    ): SearchResultDto<JsonObject>

    @Throws(ServerResponseException::class, CancellationException::class)
    suspend fun getNextPage(url: String): SearchResultDto<JsonObject>

    @Throws(ServerResponseException::class, CancellationException::class)
    suspend fun getPreviousPage(url: String): SearchResultDto<JsonObject>
}