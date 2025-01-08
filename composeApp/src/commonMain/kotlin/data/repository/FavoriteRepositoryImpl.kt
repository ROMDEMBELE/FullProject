package data.repository

import data.database.sqlDelight.SqlDatabase
import domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class FavoriteRepositoryImpl(private val database: SqlDatabase) : FavoriteRepository {

    override fun addToFavorite(slug: String) {
        database.insertFavorite(slug)
    }

    override fun removeFromFavorite(slug: String) {
        database.removeFavorite(slug)
    }

    override suspend fun isFavorite(slug: String): Boolean {
        return database.getAllFavorites().firstOrNull()?.contains(slug) ?: false
    }

    override fun getFavorites(): Flow<List<String>> {
        return database.getAllFavorites()
    }

}