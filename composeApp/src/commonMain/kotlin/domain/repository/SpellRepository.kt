package domain.repository

import domain.model.Level
import domain.model.spell.Spell
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.cancellation.CancellationException

interface SpellRepository {

    suspend fun addFavorite(spell: Spell)

    suspend fun removeFavorite(key: String)

    suspend fun getFavorites(): Flow<List<Spell>>

    suspend fun getFavorite(key: String): Spell?

    @Throws(
        ServerResponseException::class,
        CancellationException::class,
        NoSuchElementException::class
    )
    suspend fun getByKey(key: String): Spell

    @Throws(
        ServerResponseException::class,
        CancellationException::class
    )
    suspend fun search(
        name: String,
        min: Level = Level.LEVEL_0,
        max: Level = Level.LEVEL_10
    ): Flow<List<Spell>>

}