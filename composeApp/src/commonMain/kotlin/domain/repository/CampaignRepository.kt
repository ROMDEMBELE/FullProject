package domain.repository

import data.database.sqlDelight.SqlDatabase
import domain.model.Campaign
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.dembeyo.data.CampaingnDbo

class CampaignRepository(private val database: SqlDatabase) {

    private fun CampaingnDbo.toDomain() = Campaign(id, fullName, description)

    fun getById(id: Long): Flow<Campaign?> =
        database.getCampaignById(id).map { it?.toDomain() }

    fun getAll(): Flow<List<Campaign>> =
        database.getAllCampaign().map { list -> list.map { it.toDomain() } }

    fun createOrUpdate(
        id: Long?,
        name: String,
        description: String,
    ): Long? = database.insertOrUpdateCampaign(id, name, description)

    fun delete(id: Long) {
        database.deleteCampaignById(id)
    }
}