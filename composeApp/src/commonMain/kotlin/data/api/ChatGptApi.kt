package data.api

import data.api.dto.chatGpt.ChatGptResponseFormatDto
import kotlinx.serialization.json.JsonObject

interface ChatGptApi {

    suspend fun processMonster(jsonObject: JsonObject, format: ChatGptResponseFormatDto)

    suspend fun processMonsters(monsters: List<JsonObject>, format: ChatGptResponseFormatDto)
}