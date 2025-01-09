package data.api.impl

import data.api.ChatGptApi
import data.api.dto.chatGpt.ChatGptMessageDto
import data.api.dto.chatGpt.ChatGptRequestDto
import data.api.dto.chatGpt.ChatGptResponseDto
import data.api.dto.chatGpt.ChatGptResponseFormatDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

class ChatGptApiImpl(private val client: HttpClient) : ChatGptApi {

    override suspend fun processMonster(
        jsonObject: JsonObject,
        format: ChatGptResponseFormatDto
    ) {
        val request = ChatGptRequestDto(
            model = DEFAULT_MODEL,
            responseFormat = format,
            messages = listOf(
                ChatGptMessageDto(
                    role = "system",
                    content = "You are a validator and transformer of JSON data. Transform the input of JSON D&D Monster and return a cleaned JSON object."
                ),
                ChatGptMessageDto(
                    role = "user",
                    content = "Here is a raw JSON string representing a monster: ${
                        Json.encodeToString(
                            jsonObject
                        )
                    }"
                )
            )
        )
        val response = client.post(BASE_URL) {
            header("Authorization", "Bearer $API_KEY")
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        if (response.status.isSuccess()) {
            val correctedJson = response.body<ChatGptResponseDto>().choices.first().message.content
            return Json.decodeFromString(correctedJson)
        } else {
            throw ServerResponseException(
                response, "Failed to process $request: ${response.status}"
            )
        }
    }

    override suspend fun processMonsters(
        monsters: List<JsonObject>,
        format: ChatGptResponseFormatDto
    ) {
        val request = ChatGptRequestDto(
            model = DEFAULT_MODEL,
            messages = listOf(
                ChatGptMessageDto(
                    role = "system",
                    content = "You are a validator and transformer of JSON data. Transform the input list of JSON D&D Monsters and return a cleaned JSON object."
                ),
                ChatGptMessageDto(
                    role = "user",
                    content = "Here is a raw JSON string representing ${monsters.size} monsters: ${
                        Json.encodeToString(
                            monsters
                        )
                    }."
                )
            ),
            responseFormat = format
        )

        val response = client.post(BASE_URL) {
            header("Authorization", "Bearer $API_KEY")
            contentType(ContentType.Application.Json)
            setBody(request)
        }

        if (response.status.isSuccess()) {
            val correctedJson = response.body<ChatGptResponseDto>().choices.first().message.content
        } else {
            throw ServerResponseException(
                response, "Failed to process $request: ${response.status}"
            )
        }
    }

    companion object {

        private const val DEFAULT_MODEL = "gpt-4o-mini"

        private const val BASE_URL = "https://api.openai.com/v1/chat/completions"

        private const val API_KEY = ""
    }

}