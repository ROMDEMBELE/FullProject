package data.api.dto.chatGpt

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data Transfer Object for Chat GPT request.
 *
 * @property model The model to use for the request (e.g. "gpt-3.5-turbo").
 * @property messages The list of messages to include in the request.
 * @property responseFormat The format of the response (e.g. "json").
 */
@Serializable
data class ChatGptRequestDto(
    @SerialName("model")
    val model: String,
    @SerialName("messages")
    val messages: List<ChatGptMessageDto> = emptyList(),
    @SerialName("response_format")
    val responseFormat: ChatGptResponseFormatDto? = null
)