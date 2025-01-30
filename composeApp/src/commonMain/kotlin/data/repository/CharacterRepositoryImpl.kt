package data.repository

import data.database.realm.CharacterDbo
import data.database.realm.RealmDatabase
import domain.model.Alignment
import domain.model.Level
import domain.model.character.Character
import domain.model.character.CharacterClass
import domain.repository.CharacterRepository
import io.realm.kotlin.ext.isManaged
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CharacterRepositoryImpl(private val realm: RealmDatabase) : CharacterRepository {


    override suspend fun getById(id: String): Character {
        return realm.queryCharacterWithId(id)?.toCharacter()
            ?: throw NoSuchElementException("Character with id $id not found")
    }

    override suspend fun delete(id: String) {
        realm.writeBlocking {
            realm.queryCharacterWithId(id)?.let { delete(it) }
                ?: throw NoSuchElementException("Character with id $id not found")
        }
    }

    override fun getAll(): Flow<List<Character>> {
        return realm.observeAllCharacters().map {
            it.list.map { characterDbo ->
                characterDbo.toCharacter()
            }
        }
    }

    override suspend fun save(
        uuid: String?,
        campaignId: String,
        name: String,
        level: Level,
        alignment: Alignment,
        characterClass: CharacterClass,
        armorClass: Int,
        passivePerception: Int,
        hitPoint: Int,
        charisma: Int,
        dexterity: Int,
        constitution: Int,
        intelligence: Int,
        strength: Int,
        wisdom: Int,
    ) {
        val campaignDbo = realm.queryCampaignWithId(campaignId)
            ?: throw NoSuchElementException("Campaign with id $campaignId not found")

        val characterDbo: CharacterDbo = if (uuid == null) {
            CharacterDbo()
        } else {
            realm.queryCharacterWithId(uuid)
        } ?: throw NoSuchElementException("Character with uuid $uuid not found")

        realm.writeBlocking {
            characterDbo.apply {
                this.name = name
                this.level = level.level
                this.alignment = alignment.name
                this.characterClass = characterClass.name
                this.armorClass = armorClass
                this.passivePerception = passivePerception
                this.hitPoint = hitPoint
                this.charisma = charisma
                this.dexterity = dexterity
                this.constitution = constitution
                this.intelligence = intelligence
                this.strength = strength
                this.wisdom = wisdom
            }.also { characterDbo ->
                if (!characterDbo.isManaged()) copyToRealm(characterDbo)
                campaignDbo.apply {
                    listOfCharacters.add(characterDbo)
                }
            }
        }

    }

}