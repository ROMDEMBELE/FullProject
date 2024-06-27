package data.database

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
import org.dembeyo.data.ConditionDbo
import org.dembeyo.data.MonsterDbo
import org.dembeyo.data.MySqlDelightDatabase
import org.dembeyo.data.RaceDbo
import org.dembeyo.data.SpellDbo

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

    fun getMonsterById(id: String): Flow<MonsterDbo?> =
        database.monsterQueries.selectOne(id).asFlow().mapToOne(Dispatchers.IO)

    fun getAllMonsters(): Flow<List<MonsterDbo>> =
        database.monsterQueries.selectAll().asFlow().mapToList(Dispatchers.IO)

    fun insertMonster(index: String, name: String, challenge: Double) {
        database.monsterQueries.insertOrIgnore(
            id = index, name = name, challenge = challenge, isFavorite = 0L
        )
    }

    fun updateMonsterFavoriteStatus(index: String, boolean: Boolean) =
        database.monsterQueries.setFavorite(id = index, isFavorite = if (boolean) 1L else 0L)

    fun getSpellById(id: String): Flow<SpellDbo?> =
        database.spellQueries.selectOne(id).asFlow().mapToOne(Dispatchers.IO)

    fun getAllSpells(): Flow<List<SpellDbo>> =
        database.spellQueries.selectAll().asFlow().mapToList(Dispatchers.IO)

    fun insertSpell(index: String, name: String, level: Long) {
        database.spellQueries.insertOrIgnore(
            id = index, name = name, level = level, isFavorite = 0L
        )
    }

    fun updateSpellFavoriteStatus(index: String, boolean: Boolean) =
        database.spellQueries.setFavorite(id = index, isFavorite = if (boolean) 1L else 0L)

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
        fullName: String,
        description: String,
        progress: Long
    ): Long? {
        database.campaignQueries.insertOrUpdate(id, fullName, description, progress)
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

    // section Condition
    fun getAllCondition(): Flow<List<ConditionDbo>> =
        database.conditionQueries.selectAll().asFlow().mapToList(Dispatchers.IO)

    fun getConditionById(id: String): Flow<ConditionDbo?> =
        database.conditionQueries.selectById(id).asFlow().mapToOne(Dispatchers.IO)
}