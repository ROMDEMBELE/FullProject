package data.api.dto.chatGpt

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data Transfer Object for Chat GPT request.
 *
 * @property role The role of the message (e.g. "user").
 * @property content The content of the message.
 *
 */
@Serializable
data class ChatGptMessageDto(
    @SerialName("role") val role: String,
    @SerialName("content") val content: String
)