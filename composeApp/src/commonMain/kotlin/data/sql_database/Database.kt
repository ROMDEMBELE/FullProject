package data.sql_database

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import org.dembeyo.data.Character
import org.dembeyo.data.MySqlDelightDatabase

class Database(driverFactory: DriverFactory) {

    private val database = MySqlDelightDatabase(driverFactory.createDriver())

    fun getAllCharacter(): Flow<List<Character>> =
        database.characterQueries.selectAll().asFlow()
            .mapToList(Dispatchers.IO)

    fun getCharacterById(id: Long): Flow<Character?> =
        database.characterQueries.selectById(id).asFlow().mapToOne(Dispatchers.IO)

    fun createCharacter(
        name: String,
        age: Long,
        race: String,
        characterClass: String,
        subclass: String,
        level: Long,
        cha: Long,
        con: Long,
        dex: Long,
        int: Long,
        str: Long,
        wis: Long,
        features: String
    ) {
        database.characterQueries.insert(
            name = name,
            age = age,
            race = race,
            class_ = characterClass, // 'class' is a reserved keyword in Kotlin, so use 'class_'
            subclass = subclass,
            level = level,
            cha = cha,
            con = con,
            dex = dex,
            int = int,
            str = str,
            wis = wis,
            features = features
        )
    }

    fun deleteCharacterById(id: Long) = database.characterQueries.deleteById(id)
}