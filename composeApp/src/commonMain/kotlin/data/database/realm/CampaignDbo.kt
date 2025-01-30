package data.database.realm

import domain.model.campaign.Campaign
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import io.realm.kotlin.types.annotations.PrimaryKey
import org.dembeyo.data.MonsterDbo

class CampaignDbo : RealmObject {
    @PrimaryKey
    var _id: RealmUUID = RealmUUID.random()
    var name: String = ""
    var description: String = ""
    var listOfCharacters: RealmList<CharacterDbo> = realmListOf()
    var listOfEncounters: RealmList<EncounterDbo> = realmListOf()

    fun toCampaign(
        getCharacterById: (String) -> CharacterDbo?,
        getMonsterById: (String) -> MonsterDbo?
    ): Campaign {
        return Campaign(
            uuid = _id.toString(),
            name = name,
            description = description,
            characters = listOfCharacters.map { it.toCharacter() },
            encounters = listOfEncounters.map { it.toEncounter(getCharacterById, getMonsterById) }
        )
    }


}