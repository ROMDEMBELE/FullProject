package data.api.impl

import data.api.FeatApi
import data.api.dto.SearchResultDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.http.isSuccess
import kotlinx.serialization.json.JsonObject

class FeatApiImpl(client: HttpClient) : FeatApi, Open5eApiImpl(client) {

    override suspend fun fetchAll(): SearchResultDto<JsonObject> {
        val response = client.get(BASE_URL)
        if (response.status.isSuccess()) {
            return response.body()
        } else {
            throw ServerResponseException(response, "get feats failed : ${response.status}")
        }
    }

    companion object {
        private const val BASE_URL = "https://api.open5e.com/v2/feats"
    }
}