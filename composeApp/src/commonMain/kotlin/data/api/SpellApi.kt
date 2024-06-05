package data.api

import data.dto.SearchResultDto
import data.dto.SpellDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class SpellApi {

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
        install(HttpCache) {

        }
    }

    suspend fun getSpells(
        levelList: List<String> = emptyList(),
        schoolList: List<String> = emptyList()
    ): SearchResultDto<SpellDto> {
        val response = client.get("$BASE_URL/api/spells") {
            url {
                levelList.forEach {
                    parameters.append("level", it)
                }
                schoolList.forEach {
                    parameters.append("school", it)
                }
            }
            headers {
                append(HttpHeaders.CacheControl, "max-age=3600")
                append("Accept", "application/json")
            }
        }
        return when (response.status) {
            HttpStatusCode.OK -> response.body() as SearchResultDto<SpellDto>
            else -> throw ServerResponseException(
                response,
                "/api/spells failed : status ${response.status}"
            )
        }
    }

    suspend fun getSpell(index: String): SpellDto? {
        val response = client.get("$BASE_URL/api/spells/$index") {
            headers {
                append(HttpHeaders.CacheControl, "max-age=3600")
                append("Accept", "application/json")
            }
        }
        return when (response.status) {
            HttpStatusCode.OK -> response.body() as SpellDto
            else -> null
        }
    }

    companion object {
        private const val BASE_URL = "https://www.dnd5eapi.co"
    }
}