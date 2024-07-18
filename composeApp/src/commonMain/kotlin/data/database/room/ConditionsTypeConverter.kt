package data.database.room

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import domain.model.encounter.Condition

class ConditionsTypeConverter {

    @TypeConverter
    fun fromConditions(conditions: List<Condition>): String {
        return conditions.joinToString(",") { it.name }
    }

    @TypeConverter
    fun toConditions(conditions: String): List<Condition> {
        return conditions.split(",").map { Condition.valueOf(it) }
    }
}