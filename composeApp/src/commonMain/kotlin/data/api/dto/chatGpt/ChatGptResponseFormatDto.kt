package data.api.dto.chatGpt

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

/**
 * Data Transfer Object for Chat GPT response format.
 *
 * @property type The type of the response format (e.g. "json").
 * @property schema The schema of the response format.
 */
@Serializable
data class ChatGptResponseFormatDto(
    @SerialName("type")
    val type: String,
    @SerialName("json_schema")
    val schema: JsonObject,
)