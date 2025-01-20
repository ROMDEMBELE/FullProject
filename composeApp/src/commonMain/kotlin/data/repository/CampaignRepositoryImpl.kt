package data.repository

import data.database.realm.CampaignDbo
import data.database.realm.RealmDatabase
import data.database.sqlDelight.SqlDatabase
import domain.model.campaign.Campaign
import domain.repository.CampaignRepository
import io.realm.kotlin.ext.isManaged
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

    override suspend fun createOrUpdate(
        id: String?,
        name: String,
        description: String,
    ) {
        realm.writeBlocking {
            ((if (id == null) CampaignDbo() else realm.queryCampaignWithId(id)))
                ?.apply {
                    this.name = name
                    this.description = description
                }
                ?.also { campaignDbo ->
                    if (!campaignDbo.isManaged()) copyToRealm(campaignDbo)
                }
                ?: throw NoSuchElementException("Campaign with id $id not found")
        }

    }

    override suspend fun delete(id: String) {
        realm.writeBlocking {
            realm.queryCampaignWithId(id)?.let { delete(it) }
                ?: throw NoSuchElementException("Campaign with id $id not found")
        }
    }
}