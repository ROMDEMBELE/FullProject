package data.dto

import kotlinx.serialization.Serializable

@Serializable
open class ReferenceDto(
    val index: String,
    val name: String,
    val url: String
)

