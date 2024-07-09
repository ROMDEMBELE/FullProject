package data.database.room

import PlatformContext
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers

actual fun createDatabase(context: PlatformContext): RoomDatabase.Builder<EncounterDatabase> {
    val dbFile = context.getDatabasePath("encounter.db")
    return Room.databaseBuilder<EncounterDatabase>(
        context = context.applicationContext,
        name = dbFile.absolutePath
    )
        .fallbackToDestructiveMigrationOnDowngrade(true)
        .setDriver(BundledSQLiteDriver()) // Very important
        .setQueryCoroutineContext(Dispatchers.IO)
}