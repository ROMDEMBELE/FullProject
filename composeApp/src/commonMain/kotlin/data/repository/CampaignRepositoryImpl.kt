package data.repository

import data.database.realm.CampaignDbo
import data.database.realm.RealmDatabase
import data.database.sqlDelight.SqlDatabase
import domain.model.campaign.Campaign
import domain.repository.CampaignRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.coroutines.cancellation.CancellationException

class CampaignRepositoryImpl(
    private val sqlDatabase: SqlDatabase,
    private val realm: RealmDatabase
) : CampaignRepository {

    @Throws(
        NoSuchElementException::class,
        CancellationException::class,
        IllegalArgumentException::class
    )
    override suspend fun getById(id: String): Campaign {
        return realm.queryCampaignWithId(id)
            ?.toCampaign(realm::queryCharacterWithId, sqlDatabase::getMonsterById)
            ?: throw NoSuchElementException("Campaign with id $id not found")
    }

    override fun getAll(): Flow<List<Campaign>> {
        return realm.observeAllCampaigns().map {
            it.list.map { campaignDbo ->
                campaignDbo.toCampaign(
                    realm::queryCharacterWithId,
                    sqlDatabase::getMonsterById
                )
            }
        }
    }

    private fun create(name: String, description: String) {
        val dbo = CampaignDbo().apply {
            this.name = name
            this.description = description
        }
        realm.writeBlocking { copyToRealm(dbo) }
    }

    private fun update(id: String, name: String, description: String) {
        val dbo: CampaignDbo = realm.queryCampaignWithId(id)
            ?: throw NoSuchElementException("Campaign with id $id not found")

        realm.writeBlocking {
            findLatest(dbo)?.apply {
                this.name = name
                this.description = description
            }
        }
    }

    override suspend fun createOrUpdate(
        id: String?,
        name: String,
        description: String,
    ) {
        if (id == null) {
            create(name, description)
        } else {
            update(id, name, description)
        }
    }

    override suspend fun delete(id: String) {
        val dbo: CampaignDbo = realm.queryCampaignWithId(id)
            ?: throw NoSuchElementException("Campaign with id $id not found")
        realm.writeBlocking { delete(dbo) }
    }
}