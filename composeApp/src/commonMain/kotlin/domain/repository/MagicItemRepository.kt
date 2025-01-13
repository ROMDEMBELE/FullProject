package domain.repository

import domain.model.magicItem.ItemRarity
import domain.model.magicItem.MagicItem
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.cancellation.CancellationException

interface MagicItemRepository {

    suspend fun addFavorite(magicItem: MagicItem)

    suspend fun removeFavorite(key: String)

    suspend fun getFavorites(): Flow<List<MagicItem>>

    suspend fun getFavorite(key: String): MagicItem?

    @Throws(
        ServerResponseException::class,
        NoSuchElementException::class,
        CancellationException::class
    )
    suspend fun getByKey(key: String): MagicItem

    @Throws(
        ServerResponseException::class,
        CancellationException::class
    )
    suspend fun search(
        name: String,
        rarity: ItemRarity? = null,
    ): Flow<List<MagicItem>>
}