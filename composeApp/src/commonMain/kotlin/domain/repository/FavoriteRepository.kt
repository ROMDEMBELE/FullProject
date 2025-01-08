package domain.repository

import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    /**
     * Add a item to the favorite list
     */
    fun addToFavorite(slug: String)

    /**
     * Remove a item from the favorite list
     */
    fun removeFromFavorite(slug: String)

    /**
     * Check if a item is in the favorite list
     */
    suspend fun isFavorite(slug: String): Boolean

    /**
     * Get all items slug in the favorite list
     */
    fun getFavorites(): Flow<List<String>>

}