package domain.repository

import data.database.SqlDatabase
import domain.model.character.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.dembeyo.data.CharacterDbo
import org.lighthousegames.logging.logging

class CharacterRepository(private val database: SqlDatabase) {

    private fun CharacterDbo.toDomain() = Character(
        id = id,
        fullName = fullName,
        player = player,
        level = level,
        characterClass = class_,
        armorClass = armor.toInt(),
        hitPoint = life.toInt(),
        spellSavingThrow = spellSave.toInt(),
        charisma = cha.toInt(),
        constitution = con.toInt(),
        dexterity = dex.toInt(),
        intelligence = int.toInt(),
        strength = str.toInt(),
        wisdom = wis.toInt(),
        backgroundId = background_id,
        speciesId = species_id
    )

    fun getCharacterById(id: Long): Flow<Character?> =
        database.getCharacterById(id).map { it?.toDomain() }


    fun deleteCharacter(id: Long) {
        database.deleteCharacterById(id)
    }

    fun getListOfCharacters(): Flow<List<Character>> = database.getAllCharacter().map {
        it.map { dbo -> dbo.toDomain() }
    }

    fun createOrUpdateCharacter(character: Character): Long? {
        return database.insertOrUpdateCharacter(
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
            backgroundId = character.backgroundId,
            speciesId = character.speciesId,
            _class = character.characterClass
        )
    }

    companion object {
        val Log = logging("CharacterRepository")
    }

}