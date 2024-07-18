package domain.repository

import data.database.sqlDelight.SqlDatabase
import domain.model.Level
import domain.model.character.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.dembeyo.data.CharacterDbo

class CharacterRepository(private val database: SqlDatabase) {

    private fun CharacterDbo.toDomain() = Character(
        id = id,
        fullName = fullName,
        campaignId = campaign_id,
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

    fun getById(id: Long): Flow<Character?> =
        database.getCharacterById(id).map { it?.toDomain() }

    fun getByCampaignId(campaignId: Long): Flow<List<Character>> =
        database.getAllCharacter()
            .map { list -> list.map { it.toDomain() }.filter { it.campaignId == campaignId } }

    fun deleteCharacter(id: Long) {
        database.deleteCharacterById(id)
    }

    fun getAll(): Flow<List<Character>> = database.getAllCharacter().map {
        it.map { dbo -> dbo.toDomain() }
    }

    fun createOrUpdateCharacter(
        id: Long?,
        campaignId: Long,
        fullName: String,
        player: String,
        level: Level,
        speciesId: Long,
        characterClass: String,
        backgroundId: Long,
        armorClass: Int,
        spellSavingThrow: Int,
        hitPoint: Int,
        charisma: Int,
        dexterity: Int,
        constitution: Int,
        intelligence: Int,
        strength: Int,
        wisdom: Int,
    ): Long? = database.insertOrUpdateCharacter(
        id = id,
        campaignId = campaignId,
        fullName = fullName,
        player = player,
        level = level,
        armor = armorClass.toLong(),
        life = hitPoint.toLong(),
        spellSave = spellSavingThrow.toLong(),
        cha = charisma.toLong(),
        con = constitution.toLong(),
        dex = dexterity.toLong(),
        int = intelligence.toLong(),
        str = strength.toLong(),
        wis = wisdom.toLong(),
        backgroundId = backgroundId,
        speciesId = speciesId,
        _class = characterClass,
    )
}