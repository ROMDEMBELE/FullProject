package data.api.dto

import kotlinx.serialization.Serializable

/**
 * A data class representing a search result.
 *
 * @property count The total number of results.
 * @property ignored The number of ignored results.
 * @property next The URL for the next page of results.
 * @property previous The URL for the previous page of results.
 * @property results The list of results returned by the search as a generic type T.
 */
@Serializable
data class SearchResultDto<T>(
    val count: Int = 0,
    val ignored: Int? = null,
    val next: String? = null,
    val previous: String? = null,
    val results: List<T> = emptyList()
)

