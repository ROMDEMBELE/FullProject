package data.database.realm

import domain.model.campaign.Campaign
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.dembeyo.data.MonsterDbo
import org.mongodb.kbson.ObjectId

class CampaignDbo : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var name: String = ""
    var description: String = ""
    var listOfCharacters: RealmList<CharacterDbo> = realmListOf()
    var listOfEncounters: RealmList<EncounterDbo> = realmListOf()

    fun toCampaign(
        getCharacterById: (String) -> CharacterDbo?,
        getMonsterById: (String) -> MonsterDbo?
    ): Campaign {
        return Campaign(
            uuid = _id.toHexString(),
            name = name,
            description = description,
            characters = listOfCharacters.map { it.toCharacter() },
            encounters = listOfEncounters.map { it.toEncounter(getCharacterById, getMonsterById) }
        )
    }


}