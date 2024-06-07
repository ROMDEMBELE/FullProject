package repository

import data.api.DndApi
import data.database.SqlDatabase
import domain.Ability
import domain.CharacterClass
import domain.Level
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
        database.createCharacter(
            name = name,
            age = age.toLong(),
            characterClass = characterClass.index,
            subclass = "",
            level = level.level.toLong(),
            cha = abilities[Ability.CHA]!!.toLong(),
            wis = abilities[Ability.WIS]!!.toLong(),
            str = abilities[Ability.STR]!!.toLong(),
            int = abilities[Ability.INT]!!.toLong(),
            con = abilities[Ability.CON]!!.toLong(),
            dex = abilities[Ability.DEX]!!.toLong(),
            race = "",
            features = "",
        )
    }

    companion object {
        val Log = logging("CharacterRepository")
    }

}