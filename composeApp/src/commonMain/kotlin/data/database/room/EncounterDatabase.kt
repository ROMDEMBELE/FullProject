package data.database.room

import PlatformContext
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import data.database.room.dao.CharacterFighterDao
import data.database.room.dao.EncounterDao
import data.database.room.dao.MonsterFighterDao
import data.database.room.entity.CharacterFighterEntity
import data.database.room.entity.EncounterEntity
import data.database.room.entity.MonsterFighterEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(
    entities = [
        EncounterEntity::class,
        CharacterFighterEntity::class,
        MonsterFighterEntity::class,
    ],
    version = 1
)
@TypeConverters(
    ChallengeTypeConverter::class,
    LevelTypeConverter::class,
    ConditionsTypeConverter::class
)
abstract class EncounterDatabase : RoomDatabase(), DB {

    abstract fun encounterDao(): EncounterDao

    abstract fun characterFighterDao(): CharacterFighterDao

    abstract fun monsterFighterDao(): MonsterFighterDao

    override fun clearAllTables() {
        super.clearAllTables()
    }
}

expect fun getDatabaseBuilder(context: PlatformContext): RoomDatabase.Builder<EncounterDatabase>

fun getDatabase(builder: RoomDatabase.Builder<EncounterDatabase>): EncounterDatabase {
    return builder.fallbackToDestructiveMigrationOnDowngrade(true)
        .setDriver(BundledSQLiteDriver()) // Very important
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}

// FIXME: Added a hack to resolve below issue:
// Class 'AppDatabase_Impl' is not abstract and does not implement abstract base class member 'clearAllTables'.
interface DB {
    fun clearAllTables() {}
}