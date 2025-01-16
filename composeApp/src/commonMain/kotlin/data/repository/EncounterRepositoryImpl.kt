package data.repository

import data.database.realm.EncounterDbo
import data.database.realm.FighterDbo
import data.database.realm.RealmDatabase
import data.database.sqlDelight.SqlDatabase
import domain.model.campaign.CharacterFighter
import domain.model.campaign.Encounter
import domain.model.campaign.EncounterFighter
import domain.model.campaign.MonsterFighter
import domain.repository.EncounterRepository
import io.realm.kotlin.ext.isManaged
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EncounterRepositoryImpl(
    private val realm: RealmDatabase,
    private val sqlDatabase: SqlDatabase
) :
    EncounterRepository {

    override fun getById(id: String): Encounter {
        return realm.queryEncounterWithId(id)
            ?.toEncounter(realm::queryCharacterWithId, sqlDatabase::getMonsterById)
            ?: throw NoSuchElementException("Encounter with id $id not found")
    }

    override fun getAll(): Flow<List<Encounter>> {
        return realm.observeAllEncounters().map {
            it.list.map { encounterDbo ->
                encounterDbo.toEncounter(realm::queryCharacterWithId, sqlDatabase::getMonsterById)
            }
        }
    }

    override fun save(
        id: String?,
        campaignId: String,
        title: String,
        description: String,
        turn: Int,
        fighters: List<EncounterFighter>,
        isFinished: Boolean,
    ) {
        realm.writeBlocking {
            val campaignDbo = realm.queryCampaignWithId(campaignId)
                ?: throw NoSuchElementException("Campaign with id $campaignId not found")

            ((if (id != null) realm.queryEncounterWithId(id) else EncounterDbo()))
                ?.apply {
                    this.title = title
                    this.description = description
                    this.round = turn
                    this.fighters.clear()
                    this.fighters.addAll(fighters.map {
                        FighterDbo().apply {
                            this.uuid = it.uuid
                            this.characterUuid = (it as? CharacterFighter)?.character?.uuid
                            this.monsterIndex = (it as? MonsterFighter)?.monster?.key
                            this.initiative = it.initiative
                            this.conditions.clear()
                            this.conditions.addAll(it.conditions.map { condition -> condition.name })
                            this.name = it.name
                            this.armorClass = it.armorClass
                            this.maxHitPoint = it.maxHitPoint
                            this.hitPoint = it.currentHitPoint
                            this.passivePerception = it.passivePerception
                        }
                    })
                    this.isFinished = isFinished
                }?.also { encounterDbo ->
                    if (!encounterDbo.isManaged()) copyToRealm(encounterDbo)

                    campaignDbo.apply { listOfEncounters.add(encounterDbo) }
                } ?: throw NoSuchElementException("Encounter with id $id not found")
        }
    }

    override suspend fun delete(id: Long) {
        realm.writeBlocking {
            realm.queryEncounterWithId(id.toString())?.let { delete(it) }
                ?: throw NoSuchElementException("Encounter with id $id not found")
        }

    }
}