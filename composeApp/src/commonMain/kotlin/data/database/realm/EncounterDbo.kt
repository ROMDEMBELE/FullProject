package data.database.realm

import domain.model.Environment
import domain.model.campaign.Encounter
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import io.realm.kotlin.types.annotations.PrimaryKey
import org.dembeyo.data.MonsterDbo

class EncounterDbo : RealmObject {
    @PrimaryKey
    var _id: RealmUUID = RealmUUID.random()
    var title: String = ""
    var description: String = ""
    var round: Int = 0
    var isFinished: Boolean = false
    var environment: String = ""
    var fighters: RealmList<FighterDbo> = realmListOf()

    @Throws(NoSuchElementException::class, IllegalArgumentException::class)
    fun toEncounter(
        getCharacterById: (String) -> CharacterDbo?,
        getMonsterById: (String) -> MonsterDbo?
    ) = Encounter(
        uuid = _id.toString(),
        title = title,
        description = description,
        fighters = fighters.map { it.toFighter(getCharacterById, getMonsterById) },
        turn = round,
        isFinished = isFinished,
        environment = Environment.valueOf(environment)
    )
}