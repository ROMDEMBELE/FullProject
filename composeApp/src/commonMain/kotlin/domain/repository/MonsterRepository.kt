package domain.repository

import domain.model.monster.Challenge
import domain.model.monster.Monster
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.cancellation.CancellationException

interface MonsterRepository {

    suspend fun addFavorite(monster: Monster)

    suspend fun removeFavorite(key: String)

    suspend fun getFavorites(): Flow<List<Monster>>

    suspend fun getFavorite(key: String): Monster?

    @Throws(
        ServerResponseException::class,
        NoSuchElementException::class,
        CancellationException::class
    )
    suspend fun getByKey(slug: String): Monster

    @Throws(
        ServerResponseException::class,
        CancellationException::class
    )
    suspend fun search(
        name: String,
        min: Challenge = Challenge.CR_0,
        max: Challenge = Challenge.CR_30
    ): Flow<List<Monster>>
}