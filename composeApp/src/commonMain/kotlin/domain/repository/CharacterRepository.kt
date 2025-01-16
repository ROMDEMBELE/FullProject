package domain.repository

import domain.model.Alignment
import domain.model.Level
import domain.model.character.Character
import domain.model.character.CharacterClass
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.cancellation.CancellationException

interface CharacterRepository {

    @Throws(NoSuchElementException::class, CancellationException::class)
    suspend fun getById(id: String): Character

    @Throws(NoSuchElementException::class, CancellationException::class)
    suspend fun delete(id: String)

    fun getAll(): Flow<List<Character>>

    suspend fun save(
        uuid: String?,
        campaignId: String,
        name: String,
        level: Level,
        alignment: Alignment,
        characterClass: CharacterClass,
        armorClass: Int,
        passivePerception: Int,
        hitPoint: Int,
        charisma: Int,
        dexterity: Int,
        constitution: Int,
        intelligence: Int,
        strength: Int,
        wisdom: Int,
    )
}