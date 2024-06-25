package data.database

import app.cash.sqldelight.EnumColumnAdapter
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import domain.model.Level
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import org.dembeyo.data.CharacterDbo
import org.dembeyo.data.MonsterDbo
import org.dembeyo.data.MySqlDelightDatabase
import org.dembeyo.data.SpellDbo

class SqlDatabase(driverFactory: DriverFactory) {

    private val database = MySqlDelightDatabase(
        driver = driverFactory.createDriver(), CharacterDboAdapter = CharacterDbo.Adapter(
            levelAdapter = EnumColumnAdapter(),
        )
    )

    fun getMonsterById(id: String): Flow<MonsterDbo?> =
        database.monsterQueries.selectOne(id).asFlow().mapToOne(Dispatchers.IO)

    fun getAllMonsters(): Flow<List<MonsterDbo>> =
        database.monsterQueries.selectAll().asFlow().mapToList(Dispatchers.IO)

    fun createMonster(index: String, name: String, challenge: Double) {
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

    fun createSpell(index: String, name: String, level: Long) {
        database.spellQueries.insertOrIgnore(
            id = index, name = name, level = level, isFavorite = 0L
        )
    }

    fun updateSpellFavoriteStatus(index: String, boolean: Boolean) =
        database.spellQueries.setFavorite(id = index, isFavorite = if (boolean) 1L else 0L)

    fun getAllCharacter(): Flow<List<CharacterDbo>> =
        database.characterQueries.selectAll().asFlow().mapToList(Dispatchers.IO)

    fun getCharacterById(id: Long): Flow<CharacterDbo?> =
        database.characterQueries.selectById(id).asFlow().mapToOne(Dispatchers.IO)

    fun deleteCharacterById(id: Long) = database.characterQueries.deleteById(id)

    fun insertOrUpdate(
        id: Long?,
        fullName: String,
        player: String,
        level: Level,
        armor: Long,
        life: Long,
        spellSave: Long,
        cha: Long,
        con: Long,
        dex: Long,
        int: Long,
        str: Long,
        wis: Long,
    ): Long? {
        database.characterQueries.insertOrUpdate(
            id, fullName, player, level, armor, life, spellSave, cha, con, dex, int, str, wis
        )
        return database.characterQueries.lastInsertRowId().executeAsOneOrNull()
    }
}