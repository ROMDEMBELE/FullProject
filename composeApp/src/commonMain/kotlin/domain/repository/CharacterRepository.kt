package domain.repository

import data.api.DndApi
import data.database.SqlDatabase
import domain.model.Ability
import domain.model.Level
import domain.model.character.CharacterClass
import io.ktor.client.plugins.ServerResponseException
import org.lighthousegames.logging.logging

class CharacterRepository(private val dndApi: DndApi, private val database: SqlDatabase) {

    suspend fun getClasses(): List<CharacterClass> {
        try {
            val result = dndApi.getClasses()
            return result.results.map {
                CharacterClass(
                    index = it.index, name = it.name
                )
            }
        } catch (e: ServerResponseException) {
            Log.e { e.message }
            return emptyList()
        }
    }

    fun saveCharacter(
        name: String,
        age: Int,
        characterClass: CharacterClass,
        level: Level,
        abilities: Map<Ability, Int>
    ) {

    }

    companion object {
        val Log = logging("CharacterRepository")
    }

}