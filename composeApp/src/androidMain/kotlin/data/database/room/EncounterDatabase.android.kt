package data.database.room

import PlatformContext
import androidx.room.Room
import androidx.room.RoomDatabase

actual fun getDatabaseBuilder(context: PlatformContext): RoomDatabase.Builder<EncounterDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath("encounter.db")
    return Room.databaseBuilder<EncounterDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}