package data.api

import data.api.dto.SearchResultDto
import io.ktor.client.plugins.ServerResponseException
import io.ktor.serialization.JsonConvertException
import kotlinx.serialization.json.JsonObject
import kotlin.coroutines.cancellation.CancellationException

interface FeatApi : Open5eApi {

    @Throws(
        ServerResponseException::class,
        CancellationException::class,
        JsonConvertException::class
    )
    suspend fun fetchAll(): SearchResultDto<JsonObject>

}