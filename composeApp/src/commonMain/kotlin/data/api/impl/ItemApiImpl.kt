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

class ItemApiImpl(client: HttpClient) : ItemApi, Open5eApiImpl(client) {

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
                parameters.append("name__icontains", query.lowercase())
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

    companion object {
        const val BASE_URL = "https://api.open5e.com/v2/items"
    }
}