package domain.repository

import data.database.SqlDatabase
import domain.model.Campaign
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.dembeyo.data.CampaingnDbo

class CampaignRepository(private val database: SqlDatabase) {

    private fun CampaingnDbo.toDomain() = Campaign(id, fullName, description, progress.toInt())

    fun getListOfCampaign(): Flow<List<Campaign>> = database.getAllCampaign().map {
        it.map { dbo -> dbo.toDomain() }
    }

    fun getCampaignById(id: Long): Flow<Campaign?> =
        database.getCampaignById(id).map { it?.toDomain() }

    fun createOrUpdateCampaign(
        id: Long?,
        name: String,
        description: String,
        progress: Int
    ): Long? = database.insertOrUpdateCampaign(id, name, description, progress.toLong())

    fun deleteCampaign(id: Long) {
        database.deleteCampaignById(id)
    }


}