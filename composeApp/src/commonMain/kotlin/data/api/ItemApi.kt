package data.api

import data.api.dto.SearchResultDto
import io.ktor.client.plugins.ServerResponseException
import kotlinx.serialization.json.JsonObject
import kotlin.coroutines.cancellation.CancellationException

interface ItemApi : Open5eApi {

    @Throws(ServerResponseException::class, CancellationException::class)
    suspend fun getByKey(key: String): SearchResultDto<JsonObject>

    @Throws(ServerResponseException::class, CancellationException::class)
    suspend fun search(
        query: String,
        rarity: String?,
    ): SearchResultDto<JsonObject>


}