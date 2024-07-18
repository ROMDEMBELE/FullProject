package data.database.room

import PlatformContext
import androidx.room.Room
import androidx.room.RoomDatabase
import data.database.instantiateImpl // This shall show error, ignore it
import platform.Foundation.NSHomeDirectory

actual fun getDatabaseBuilder(context: PlatformContext): RoomDatabase.Builder<EncounterDatabase> {
    val dbFile = NSHomeDirectory() + "/encounter.db"
    return Room.databaseBuilder<EncounterDatabase>(
        name = dbFile,
        factory = { EncounterDatabase::class.instantiateImpl() } // This too will show error
    )
}