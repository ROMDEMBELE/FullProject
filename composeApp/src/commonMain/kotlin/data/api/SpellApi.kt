package data.api

import data.api.dto.SearchResultDto
import io.ktor.client.plugins.ServerResponseException
import io.ktor.serialization.JsonConvertException
import kotlinx.serialization.json.JsonObject
import kotlin.coroutines.cancellation.CancellationException

interface SpellApi {

    @Throws(
        ServerResponseException::class,
        CancellationException::class,
        JsonConvertException::class
    )
    suspend fun findSpell(key: String): SearchResultDto<JsonObject>

    @Throws(
        ServerResponseException::class,
        CancellationException::class,
        JsonConvertException::class
    )
    suspend fun search(query: String, minLv: Int, maxLv: Int): SearchResultDto<JsonObject>

    @Throws(
        ServerResponseException::class,
        CancellationException::class,
        JsonConvertException::class
    )
    suspend fun getByLevel(level: Int): SearchResultDto<JsonObject>

    @Throws(
        ServerResponseException::class,
        CancellationException::class,
        JsonConvertException::class
    )
    suspend fun getPreviousPage(url: String): SearchResultDto<JsonObject>

    @Throws(
        ServerResponseException::class,
        CancellationException::class,
        JsonConvertException::class
    )
    suspend fun getNextPage(url: String): SearchResultDto<JsonObject>

}