package data.api.impl

import data.api.MonsterApi
import data.api.dto.SearchResultDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.http.isSuccess
import kotlinx.serialization.json.JsonObject

class MonsterApiImpl(client: HttpClient) : MonsterApi, Open5eApiImpl(client) {

    override suspend fun getByKey(key: String): SearchResultDto<JsonObject> {
        val response = client.get(BASE_URL) {
            url {
                parameters.append("key__in", key)
            }
        }
        return if (response.status.isSuccess()) response.body()
        else throw ServerResponseException(
            response,
            "get monster by slug :$key failed : status ${response.status}"
        )
    }

    override suspend fun getByChallenge(challenge: Double): SearchResultDto<JsonObject> {
        val response = client.get(BASE_URL) {
            url {
                parameters.append("challenge_rating_decimal", challenge.toString())
            }
        }
        if (response.status.isSuccess()) {
            return response.body()
        } else {
            throw ServerResponseException(
                response,
                "get monster by challenge :$challenge failed : status ${response.status}"
            )
        }
    }

    override suspend fun search(
        query: String,
        minChallenge: Double,
        maxChallenge: Double
    ): SearchResultDto<JsonObject> {
        val response = client.get(BASE_URL) {
            url {
                parameters.append("name__icontains", query)
                parameters.append("challenge_rating_decimal__gte", minChallenge.toString())
                parameters.append("challenge_rating_decimal__lte", maxChallenge.toString())
                parameters.append("ordering", "challenge_rating_decimal")
            }
        }
        if (response.status.isSuccess()) {
            return response.body()
        } else {
            throw ServerResponseException(
                response,
                "search monsters :$query failed : status ${response.status}"
            )
        }
    }

    companion object {
        private const val BASE_URL = "https://api.open5e.com/v2/creatures"
    }
}