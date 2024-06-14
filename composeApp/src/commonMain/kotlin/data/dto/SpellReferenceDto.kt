package data.dto

import kotlinx.serialization.Serializable

@Serializable
data class SpellReferenceDto(
    val index: String,
    val name: String,
    val url: String,
    val level: Int
)