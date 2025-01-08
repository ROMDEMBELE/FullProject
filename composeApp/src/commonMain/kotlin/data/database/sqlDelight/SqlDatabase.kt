package data.database.sqlDelight

import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.EnumColumnAdapter
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import domain.model.Level
import domain.model.character.Skill
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import org.dembeyo.data.BackgroundDbo
import org.dembeyo.data.CampaingnDbo
import org.dembeyo.data.CharacterDbo
import org.dembeyo.data.MySqlDelightDatabase
import org.dembeyo.data.RaceDbo

class SqlDatabase(driverFactory: DriverFactory) {

    private val listOfSkillAdapter = object : ColumnAdapter<List<Skill>, String> {
        override fun decode(databaseValue: String): List<Skill> =
            if (databaseValue.isEmpty()) {
                listOf()
            } else {
                databaseValue.split(",").mapNotNull { Skill.fromId(it) }
            }

        override fun encode(value: List<Skill>): String = value.joinToString(",")
    }

    private val database = MySqlDelightDatabase(
        driver = driverFactory.createDriver(),
        CharacterDboAdapter = CharacterDbo.Adapter(
            levelAdapter = EnumColumnAdapter(),
        ),
        BackgroundDboAdapter = BackgroundDbo.Adapter(
            skillsAdapter = listOfSkillAdapter,
        ),
    )

    fun getAllFavorites(): Flow<List<String>> =
        database.favoriteQueries.selectAll().asFlow().mapToList(Dispatchers.IO)

    fun removeFavorite(slug: String) {
        database.favoriteQueries.delete(slug)
    }

    fun insertFavorite(slug: String) {
        database.favoriteQueries.insertOrIgnore(slug)
    }

    // section Character

    fun getAllCharacter(): Flow<List<CharacterDbo>> =
        database.characterQueries.selectAll().asFlow().mapToList(Dispatchers.IO)

    fun getCharacterById(id: Long): Flow<CharacterDbo?> =
        database.characterQueries.selectById(id).asFlow().mapToOne(Dispatchers.IO)

    fun deleteCharacterById(id: Long) = database.characterQueries.deleteById(id)

    fun insertOrUpdateCharacter(
        id: Long?,
        fullName: String,
        player: String,
        campaignId: Long,
        speciesId: Long,
        backgroundId: Long,
        level: Level,
        _class: String,
        armor: Long,
        life: Long,
        spellSave: Long,
        cha: Long,
        con: Long,
        dex: Long,
        int: Long,
        str: Long,
        wis: Long
    ): Long? {
        database.characterQueries.insertOrUpdate(
            id,
            fullName,
            player,
            campaignId,
            speciesId,
            level,
            _class,
            backgroundId,
            armor,
            life,
            spellSave,
            cha,
            con,
            dex,
            int,
            str,
            wis
        )
        return database.characterQueries.lastInsertRowId().executeAsOneOrNull()
    }

    // Section Campaign
    fun getAllCampaign(): Flow<List<CampaingnDbo>> =
        database.campaignQueries.selectAll().asFlow().mapToList(Dispatchers.IO)

    fun getCampaignById(id: Long): Flow<CampaingnDbo?> =
        database.campaignQueries.selectById(id).asFlow().mapToOne(Dispatchers.IO)

    fun insertOrUpdateCampaign(
        id: Long?,
        title: String,
        description: String,
    ): Long? {
        database.campaignQueries.insertOrUpdate(id, title, description)
        return database.campaignQueries.lastInsertRowId().executeAsOneOrNull()
    }

    fun deleteCampaignById(id: Long) = database.campaignQueries.deleteById(id)

    // section Race
    fun getAllRace(): Flow<List<RaceDbo>> =
        database.raceQueries.selectAll().asFlow().mapToList(Dispatchers.IO)

    fun getRaceById(id: Long): Flow<RaceDbo?> =
        database.raceQueries.selectById(id).asFlow().mapToOne(Dispatchers.IO)

    // section Background
    fun getAllBackground(): Flow<List<BackgroundDbo>> =
        database.backgroundQueries.selectAll().asFlow().mapToList(Dispatchers.IO)

    fun getBackgroundById(id: Long): Flow<BackgroundDbo?> =
        database.backgroundQueries.selectById(id).asFlow().mapToOne(Dispatchers.IO)
}