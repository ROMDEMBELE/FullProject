package domain.repository

import androidx.compose.ui.graphics.Color
import data.database.SqlDatabase
import domain.model.encounter.Condition
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.dembeyo.data.ConditionDbo

class ConditionRepository(private val database: SqlDatabase) {

    private fun ConditionDbo.toDomain() = Condition(id, name, Color(color), description.split("."))

    fun getAllCondition(): Flow<List<Condition>> = database.getAllCondition().map {
        it.map { dbo -> dbo.toDomain() }
    }

}