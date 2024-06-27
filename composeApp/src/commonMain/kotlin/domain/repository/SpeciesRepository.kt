package domain.repository

import data.database.SqlDatabase
import domain.model.character.Species
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.dembeyo.data.RaceDbo

class SpeciesRepository(private val database: SqlDatabase) {

    private fun RaceDbo.toDomain() =
        Species(
            id = id,
            fullName = race_name,
            cha = cha_modifier.toIntOrNull() ?: 0,
            con = con_modifier.toIntOrNull() ?: 0,
            dex = dex_modifier.toIntOrNull() ?: 0,
            int = int_modifier.toIntOrNull() ?: 0,
            str = str_modifier.toIntOrNull() ?: 0,
            wis = wis_modifier.toIntOrNull() ?: 0,
            special = special,
        )

    fun getListOfSpecies(): Flow<List<Species>> =
        database.getAllRace().map { it.map { dbo -> dbo.toDomain() } }
}