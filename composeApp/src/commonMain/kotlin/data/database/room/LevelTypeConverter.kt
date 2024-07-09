package data.database.room

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import domain.model.Level

@ProvidedTypeConverter
class LevelTypeConverter {

    @TypeConverter
    fun fromLevel(level: Level): String = level.name

    @TypeConverter
    fun toLevel(name: String): Level = Level.valueOf(name)

}