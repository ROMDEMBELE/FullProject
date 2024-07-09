package data.database.room

import PlatformContext
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import data.database.instantiateImpl // This shall show error, ignore it
import platform.Foundation.NSHomeDirectory

actual fun createDatabase(context: PlatformContext): RoomDatabase.Builder<EncounterDatabase> {
    val dbFile = NSHomeDirectory() + "/encounter.db"
    return Room.databaseBuilder<EncounterDatabase>(
        name = dbFile,
        factory = { EncounterDatabase::class.instantiateImpl() } // This too will show error
    )
        .fallbackToDestructiveMigrationOnDowngrade(true)
        .setDriver(BundledSQLiteDriver()) // Very important
        .setQueryCoroutineContext(Dispatchers.IO)
}