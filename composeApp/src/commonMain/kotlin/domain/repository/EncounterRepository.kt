package domain.repository

import data.database.room.dao.CharacterFighterDao
import data.database.room.dao.EncounterDao
import data.database.room.dao.MonsterFighterDao
import data.database.room.entity.CharacterFighterEntity
import data.database.room.entity.EncounterEntity
import data.database.room.entity.MonsterFighterEntity
import domain.model.character.Character
import domain.model.monster.Monster

class EncounterRepository(
    private val encounterDao: EncounterDao,
    private val characterFighterDao: CharacterFighterDao,
    private val monsterFighterDao: MonsterFighterDao,
) {

    suspend fun getById(id: Long) = encounterDao.getEncounterWithFightersAndConditions(id)

    suspend fun insertEncounter(campaignId: Long, title: String, description: String) {
        encounterDao.insertEncounter(
            EncounterEntity(
                campaignId = campaignId,
                title = title,
                description = description,
            )
        )
    }

    suspend fun updateEncounter(
        id: Long,
        campaignId: Long,
        title: String,
        description: String,
        turn: Int,
        isFinished: Boolean
    ) {
        encounterDao.updateEncounter(
            EncounterEntity(
                id = id,
                campaignId = campaignId,
                title = title,
                description = description,
                turn = turn,
                isFinished = isFinished
            )
        )
    }

    suspend fun updateFighter(fighter: CharacterFighterEntity) {
        characterFighterDao.updateFighter(fighter)
    }

    suspend fun insertCharacterFighter(
        encounterId: Long,
        character: Character
    ): Long {
        return characterFighterDao.insertFighter(
            CharacterFighterEntity(
                encounterId = encounterId,
                characterId = character.id,
                name = character.fullName,
                player = character.player,
                initiative = 0,
                ca = character.armorClass,
                hitPoint = character.hitPoint,
                maxHitPoint = character.hitPoint,
                level = character.level,
                conditions = emptyList()
            )
        )
    }

    suspend fun insertMonsterFighter(
        encounterId: Long,
        monster: Monster,
    ) {
        if (monster.details != null) {
            monsterFighterDao.insertFighter(
                MonsterFighterEntity(
                    encounterId = encounterId,
                    monsterIndex = monster.index,
                    name = monster.name,
                    initiative = 0,
                    ca = monster.details.armorsClass.maxOf { it.value },
                    hitPoint = monster.details.hitPoints,
                    maxHitPoint = monster.details.hitPoints,
                    challenge = monster.challenge,
                    xp = monster.details.xp,
                    conditions = emptyList()
                )
            )
        } else {
            throw IllegalStateException("Monster details is null")
        }
    }

    suspend fun deleteCharacterFighter(id: Long): Int = characterFighterDao.deleteFighter(id)

    suspend fun deleteMonsterFighter(id: Long): Int = monsterFighterDao.deleteFighter(id)

}