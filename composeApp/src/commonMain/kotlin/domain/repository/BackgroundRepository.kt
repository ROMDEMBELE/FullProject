package domain.repository

import data.database.SqlDatabase
import domain.model.character.Background
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.dembeyo.data.BackgroundDbo

class BackgroundRepository(private val database: SqlDatabase) {

    private fun BackgroundDbo.toDomain() =
        Background(
            id = id,
            name = name,
            feature = power,
            skills = skills,
        )

    fun getListOfBackground(): Flow<List<Background>> = database.getAllBackground().map { list ->
        list.map { it.toDomain() }
    }
}