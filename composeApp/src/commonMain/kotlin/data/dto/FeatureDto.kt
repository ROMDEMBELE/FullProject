package data.dto

import kotlinx.serialization.Serializable

@Serializable
data class FeatureDto(
    val index: String,
    val name: String,
    val url: String,
    val classInfo: ReferenceDto?,
    val level: Int?,
    val prerequisites: List<String>?, // Assuming prerequisites is a list of objects, modify if it's a different type
    val desc: List<String>?,
) {
}