package domain.repository

import data.api.DndApi
import data.database.SqlDatabase
import domain.model.character.Character
import domain.model.character.CharacterClass
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.dembeyo.data.CharacterDbo
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

    private fun CharacterDbo.toDomain() = Character(
        id = id,
        fullName = fullName,
        player = player,
        level = level,
        armorClass = armor.toInt(),
        hitPoint = life.toInt(),
        spellSavingThrow = spellSave.toInt(),
        charisma = cha.toInt(),
        constitution = con.toInt(),
        dexterity = dex.toInt(),
        intelligence = int.toInt(),
        strength = str.toInt(),
        wisdom = wis.toInt(),
    )

    fun getCharacterById(id: Long): Flow<Character?> =
        database.getCharacterById(id).map { it?.toDomain() }

    fun getListOfCharacters(): Flow<List<Character>> = database.getAllCharacter().map {
        it.map { dbo -> dbo.toDomain() }
    }

    fun createOrUpdateCharacter(character: Character): Long? {
        return database.insertOrUpdate(
            id = character.id,
            fullName = character.fullName,
            player = character.player,
            level = character.level,
            armor = character.armorClass.toLong(),
            life = character.hitPoint.toLong(),
            spellSave = character.spellSavingThrow.toLong(),
            cha = character.charisma.toLong(),
            con = character.constitution.toLong(),
            dex = character.dexterity.toLong(),
            int = character.intelligence.toLong(),
            str = character.strength.toLong(),
            wis = character.wisdom.toLong(),
        )
    }

    companion object {
        val Log = logging("CharacterRepository")
    }

}