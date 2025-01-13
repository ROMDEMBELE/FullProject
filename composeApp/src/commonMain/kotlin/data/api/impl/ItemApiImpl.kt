package data.api.impl

import data.api.ItemApi
import data.api.dto.SearchResultDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.serialization.json.JsonObject

class ItemApiImpl(private val client: HttpClient) : ItemApi {

    override suspend fun getByKey(key: String): SearchResultDto<JsonObject> {
        val response = client.get(BASE_URL) {
            url {
                parameters.append("key", key)
            }
        }
        if (response.status.isSuccess()) {
            return response.body()
        } else {
            throw ServerResponseException(response, response.bodyAsText())
        }
    }

    override suspend fun search(
        query: String,
        rarity: String?,
    ): SearchResultDto<JsonObject> {
        val response = client.get(BASE_URL) {
            url {
                parameters.append("name_icontains", query.lowercase())
                if (rarity != null) {
                    parameters.append("rarity", rarity)
                }
            }
        }
        if (response.status.isSuccess()) {
            return response.body()
        } else {
            throw ServerResponseException(response, response.bodyAsText())
        }
    }

    override suspend fun getNextPage(url: String): SearchResultDto<JsonObject> {
        val response = client.get(url)
        if (response.status.isSuccess()) {
            return response.body()
        } else {
            throw ServerResponseException(
                response, "get next page $url failed : ${response.status}"
            )
        }
    }

    override suspend fun getPreviousPage(url: String): SearchResultDto<JsonObject> {
        val response = client.get(url)
        if (response.status.isSuccess()) {
            return response.body()
        } else {
            throw ServerResponseException(
                response, "get previous page $url failed : ${response.status}"
            )
        }
    }

    companion object {
        const val BASE_URL = "https://api.open5e.com/v2/items"
    }
}