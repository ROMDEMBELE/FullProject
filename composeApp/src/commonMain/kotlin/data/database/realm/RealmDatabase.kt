package data.database.realm

import io.realm.kotlin.MutableRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow

class RealmDatabase {

    private val config = RealmConfiguration.create(
        schema = setOf(
            CampaignDbo::class,
            EncounterDbo::class,
            CharacterDbo::class,
            FighterDbo::class
        )
    )

    private val realm = Realm.open(config)

    fun queryCampaignWithId(id: String): CampaignDbo? =
        realm.query(CampaignDbo::class, "id == $0", id).first().find()

    fun queryCharacterWithId(id: String): CharacterDbo? =
        realm.query(CharacterDbo::class, "id == $0", id).first().find()

    fun queryEncounterWithId(id: String): EncounterDbo? =
        realm.query(EncounterDbo::class, "id == $0", id).first().find()

    fun observeAllCampaigns(): Flow<ResultsChange<CampaignDbo>> =
        realm.query(CampaignDbo::class).asFlow()

    fun observeAllCharacters(): Flow<ResultsChange<CharacterDbo>> =
        realm.query(CharacterDbo::class).asFlow()

    fun observeAllEncounters(): Flow<ResultsChange<EncounterDbo>> =
        realm.query(EncounterDbo::class).asFlow()

    fun writeBlocking(block: MutableRealm.() -> Unit) {
        realm.writeBlocking(block)
    }

    fun close() {
        realm.close()
    }

}