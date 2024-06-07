package data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ReferenceDto(
    val index: String,
    val name: String,
    val url: String
)