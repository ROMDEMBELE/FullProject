package data.dto

import kotlinx.serialization.Serializable

@Serializable
data class FeatureDto(
    val index: String,
    val name: String,
    val url: String,
    val classInfo: ClassInfoDto?,
    val level: Int?,
    val prerequisites: List<String>?, // Assuming prerequisites is a list of objects, modify if it's a different type
    val desc: List<String>?,
) {
    @Serializable
    data class ClassInfoDto(
        val index: String,
        val name: String,
        val url: String
    )
}