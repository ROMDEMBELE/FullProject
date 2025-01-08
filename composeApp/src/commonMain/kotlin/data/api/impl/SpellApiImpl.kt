package data.api.impl

import data.api.SpellApi
import data.api.dto.SearchResultDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.http.isSuccess
import kotlinx.serialization.json.JsonObject

class SpellApiImpl(private val client: HttpClient) : SpellApi {

    override suspend fun searchSpells(
        query: String,
        minLv: Int,
        maxLv: Int
    ): SearchResultDto<JsonObject> {
        val response = client.get(BASE_URL) {
            url {
                parameters.append("name__icontains", query)
                parameters.append("level__gte", minLv.toString())
                parameters.append("level__lte", maxLv.toString())
                parameters.append("ordering", "level")
            }
        }
        if (response.status.isSuccess())
            return response.body()
        else
            throw ServerResponseException(
                response,
                "search spells $query failed : ${response.status}"
            )
    }

    override suspend fun findSpell(slug: String): SearchResultDto<JsonObject> {
        val response = client.get(BASE_URL) {
            url {
                parameters.append("slug", slug)
            }
        }
        if (response.status.isSuccess())
            return response.body()
        else
            throw ServerResponseException(
                response,
                "find spell by slug $slug failed : ${response.status}"
            )
    }

    override suspend fun getPreviousPage(url: String): SearchResultDto<JsonObject> {
        val response = client.get(url)
        if (response.status.isSuccess())
            return response.body()
        else
            throw ServerResponseException(
                response,
                "get previous page $url failed : ${response.status}"
            )
    }

    override suspend fun getNextPage(url: String): SearchResultDto<JsonObject> {
        val response = client.get(url)
        if (response.status.isSuccess())
            return response.body()
        else
            throw ServerResponseException(
                response,
                "get next page $url failed : ${response.status}"
            )
    }

    companion object {
        private const val BASE_URL = "https://api.open5e.com/v2/spells"
    }
}