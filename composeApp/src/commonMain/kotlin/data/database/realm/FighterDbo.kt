package data.database.realm

import data.database.sqlDelight.toMonster
import domain.model.Condition
import domain.model.campaign.CharacterFighter
import domain.model.campaign.EncounterFighter
import domain.model.campaign.MonsterFighter
import domain.model.character.Character
import domain.model.monster.Monster
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.RealmList
import org.dembeyo.data.MonsterDbo

class FighterDbo : EmbeddedRealmObject {
    var uuid: String? = null
    var characterUuid: String? = null
    var monsterIndex: String? = null
    var initiative: Int = 0
    var conditions: RealmList<String> = realmListOf()
    var name: String = ""
    var armorClass: Int = 0
    var maxHitPoint: Int = 0
    var hitPoint: Int = 0
    var passivePerception: Int = 0

    private fun toMonsterFighter(monster: Monster): MonsterFighter {
        return MonsterFighter(
            uuid = uuid.toString(),
            monster = monster,
            initiative = initiative,
            conditions = conditions.map { Condition.valueOf(it) },
            name = name,
            armorClass = armorClass,
            maxHitPoint = maxHitPoint,
            currentHitPoint = hitPoint,
            passivePerception = passivePerception
        )
    }

    private fun toCharacterFighter(character: Character): CharacterFighter {
        return CharacterFighter(
            uuid = uuid.toString(),
            character = character,
            initiative = initiative,
            conditions = conditions.map { Condition.valueOf(it) },
            name = name,
            armorClass = armorClass,
            maxHitPoint = maxHitPoint,
            currentHitPoint = hitPoint,
            passivePerception = passivePerception
        )
    }

    @Throws(NoSuchElementException::class, IllegalArgumentException::class)
    fun toFighter(
        getCharacterById: (String) -> CharacterDbo?,
        getMonsterById: (String) -> MonsterDbo?
    ): EncounterFighter {
        return characterUuid?.let { uuid ->
            getCharacterById(uuid)?.toCharacter()?.let(::toCharacterFighter)
                ?: throw NoSuchElementException("Character with uuid $uuid not found")
        } ?: monsterIndex?.let { index ->
            getMonsterById(index)?.toMonster()?.let(::toMonsterFighter)
                ?: throw NoSuchElementException("Monster with index $index not found")
        }
        ?: throw IllegalArgumentException("Invalid fighter data - missing characterUuid or monsterIndex")
    }
}