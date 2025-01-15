package data.api.impl

import data.api.Open5eApi
import data.api.dto.SearchResultDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.http.isSuccess
import io.ktor.serialization.JsonConvertException
import kotlinx.serialization.json.JsonObject
import kotlin.coroutines.cancellation.CancellationException

open class Open5eApiImpl(protected val client: HttpClient) : Open5eApi {

    @Throws(
        ServerResponseException::class,
        CancellationException::class,
        JsonConvertException::class
    )
    override suspend fun getPage(url: String): SearchResultDto<JsonObject> {
        val response = client.get(url)
        if (response.status.isSuccess()) {
            return response.body()
        } else {
            throw ServerResponseException(
                response, "get page $url failed : ${response.status}"
            )
        }
    }

    @Throws(
        ServerResponseException::class,
        CancellationException::class,
        JsonConvertException::class
    )
    override suspend fun getUrl(url: String): JsonObject {
        val response = client.get(url)
        if (response.status.isSuccess()) {
            return response.body()
        } else {
            throw ServerResponseException(
                response, "get url $url failed : ${response.status}"
            )
        }

    }
}