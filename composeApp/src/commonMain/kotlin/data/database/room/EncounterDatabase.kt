package data.database.room

import PlatformContext
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import data.database.room.dao.CharacterFighterDao
import data.database.room.dao.EncounterDao
import data.database.room.dao.MonsterFighterDao
import data.database.room.entity.CharacterFighterEntity
import data.database.room.entity.EncounterEntity
import data.database.room.entity.MonsterFighterEntity

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
)
abstract class EncounterDatabase : RoomDatabase() {
    abstract fun encounterDao(): EncounterDao
    abstract fun characterFighterDao(): CharacterFighterDao
    abstract fun monsterFighterDao(): MonsterFighterDao
}

expect fun createDatabase(context: PlatformContext): RoomDatabase.Builder<EncounterDatabase>