package data.api

import data.dto.ClassDto
import data.dto.FeatureDto
import data.dto.ReferenceDto
import data.dto.SearchResultDto
import data.dto.SpellDto
import data.dto.SpellReferenceDto
import data.dto.monster.MonsterDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headers
import io.ktor.serialization.JsonConvertException
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlin.coroutines.cancellation.CancellationException

class DndApi {

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
        headers {
            append(HttpHeaders.CacheControl, "max-age=3600")
            append("Accept", "application/json")
        }
    }

    suspend fun getMonstersByChallenge(challengeRatings: Double): SearchResultDto<ReferenceDto> {
        val response = client.get("$BASE_URL/api/monsters") {
            url {
                parameters.append("challenge_rating", challengeRatings.toString())
            }
        }
        when (response.status) {
            HttpStatusCode.OK -> return response.body() as SearchResultDto<ReferenceDto>
            else -> throw ServerResponseException(
                response,
                "/api/monsters failed : status ${response.status}"
            )
        }
    }

    @Throws(
        ServerResponseException::class,
        CancellationException::class,
        JsonConvertException::class,
        ServerResponseException::class
    )
    suspend fun getMonsterByIndex(index: String): MonsterDto? {
        val response = client.get("$BASE_URL/api/monsters/$index")
        return when (response.status) {
            HttpStatusCode.OK -> return response.body() as MonsterDto
            else -> null
        }
    }

    suspend fun getClasses(): SearchResultDto<ClassDto> {
        val response = client.get("$BASE_URL/api/classes")
        return when (response.status) {
            HttpStatusCode.OK -> response.body() as SearchResultDto<ClassDto>
            else -> throw ServerResponseException(
                response,
                "/api/classes failed : status ${response.status}"
            )
        }
    }

    suspend fun getClassByIndex(index: String): ClassDto? {
        val response = client.get("$BASE_URL/api/classes/$index")
        return when (response.status) {
            HttpStatusCode.OK -> response.body() as ClassDto
            else -> null
        }
    }

    suspend fun getFeatures(): SearchResultDto<FeatureDto> {
        val response = client.get("$BASE_URL/api/features")
        return when (response.status) {
            HttpStatusCode.OK -> response.body() as SearchResultDto<FeatureDto>
            else -> throw ServerResponseException(
                response,
                "/api/features failed : status ${response.status}"
            )
        }
    }

    suspend fun getFeature(index: String): FeatureDto? {
        val response = client.get("$BASE_URL/api/features/$index")
        return when (response.status) {
            HttpStatusCode.OK -> response.body() as FeatureDto
            else -> null
        }
    }

    suspend fun getSpellByLevelOrSchool(
        levelList: List<String> = emptyList(),
        schoolList: List<String> = emptyList()
    ): SearchResultDto<SpellReferenceDto> {
        val response = client.get("$BASE_URL/api/spells") {
            url {
                levelList.forEach {
                    parameters.append("level", it)
                }
                schoolList.forEach {
                    parameters.append("school", it)
                }
            }
        }
        return when (response.status) {
            HttpStatusCode.OK -> response.body() as SearchResultDto<SpellReferenceDto>
            else -> throw ServerResponseException(
                response,
                "/api/spells failed : status ${response.status}"
            )
        }
    }

    suspend fun getSpellByIndex(index: String): SpellDto? {
        val response = client.get("$BASE_URL/api/spells/$index")
        return when (response.status) {
            HttpStatusCode.OK -> response.body() as SpellDto
            else -> null
        }
    }

    companion object {
        private const val BASE_URL = "https://www.dnd5eapi.co"
    }
}