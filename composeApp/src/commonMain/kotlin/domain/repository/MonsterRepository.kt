package domain.repository

import domain.model.monster.Challenge
import domain.model.monster.Monster
import domain.model.monster.MonsterReference
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.cancellation.CancellationException

interface MonsterRepository {

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
    ): Flow<List<MonsterReference>>
}