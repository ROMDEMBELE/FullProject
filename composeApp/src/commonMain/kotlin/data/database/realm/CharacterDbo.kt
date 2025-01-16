package data.database.realm

import domain.model.Alignment
import domain.model.Level
import domain.model.character.Character
import domain.model.character.CharacterClass
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class CharacterDbo : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var name: String = ""
    var level: Int = 0
    var alignment: String = ""
    var characterClass: String = ""
    var armorClass: Int = 0
    var passivePerception: Int = 0
    var hitPoint: Int = 0
    var charisma: Int = 0
    var dexterity: Int = 0
    var constitution: Int = 0
    var intelligence: Int = 0
    var strength: Int = 0
    var wisdom: Int = 0

    fun toCharacter() = Character(
        uuid = _id.toHexString(),
        name = name,
        level = Level.fromInt(level),
        alignment = Alignment.valueOf(alignment),
        characterClass = CharacterClass.valueOf(characterClass),
        armorClass = armorClass,
        passivePerception = passivePerception,
        hitPoint = hitPoint,
        charisma = charisma,
        dexterity = dexterity,
        constitution = constitution,
        intelligence = intelligence,
        strength = strength,
        wisdom = wisdom
    )
}