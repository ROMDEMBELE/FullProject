package repository

import data.api.DndApi
import data.sql_database.Database
import domain.Ability
import domain.DndClass
import domain.Level
import io.ktor.client.plugins.ServerResponseException
import org.lighthousegames.logging.logging

class CharacterRepository(private val dndApi: DndApi, private val database: Database) {

    suspend fun getClasses(): List<DndClass> {
        try {
            val result = dndApi.getClasses()
            return result.results.map {
                DndClass(
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
        characterClass: DndClass,
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