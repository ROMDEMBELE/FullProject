package data.dto

import kotlinx.serialization.Serializable

@Serializable
data class SearchResultDto<T>(
    val count: Int = 0,
    val results: List<T> = emptyList()
)

