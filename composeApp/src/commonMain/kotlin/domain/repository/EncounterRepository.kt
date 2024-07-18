package domain.repository

import data.database.room.dao.CharacterFighterDao
import data.database.room.dao.EncounterDao
import data.database.room.dao.MonsterFighterDao
import data.database.room.entity.CharacterFighterEntity
import data.database.room.entity.EncounterEntity
import data.database.room.entity.EncounterWithFightersAndConditions
import data.database.room.entity.MonsterFighterEntity
import domain.model.character.Character
import domain.model.encounter.CharacterFighter
import domain.model.encounter.Encounter
import domain.model.encounter.MonsterFighter
import domain.model.monster.Monster
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EncounterRepository(
    private val encounterDao: EncounterDao,
    private val characterFighterDao: CharacterFighterDao,
    private val monsterFighterDao: MonsterFighterDao,
) {

    private fun CharacterFighterEntity.toDomain() = CharacterFighter(
        id = id,
        characterId = characterId,
        name = name,
        player = player,
        initiative = initiative,
        ca = ca,
        maxHitPoint = maxHitPoint,
        level = level,
        conditions = conditions,
        armorClass = ca,
        spellSavingThrow = null,
        currentHitPoint = hitPoint
    )

    private fun MonsterFighterEntity.toDomain() = MonsterFighter(
        id = id,
        name = name,
        initiative = initiative,
        conditions = conditions,
        armorClass = ca,
        spellSavingThrow = null,
        maxHitPoint = maxHitPoint,
        currentHitPoint = hitPoint,
        index = monsterIndex,
        xp = xp,
        challenge = challenge
    )

    private fun EncounterWithFightersAndConditions.toDomain() = Encounter(
        id = encounter.id,
        campaignId = encounter.campaignId,
        title = encounter.title,
        description = encounter.description,
        turn = encounter.turn,
        isFinished = encounter.isFinished,
        inProgress = encounter.inProgress,
        fighters = monsters.map { it.toDomain() } + characters.map { it.toDomain() },
    )

    suspend fun getByCampaignId(campaignId: Long): Flow<List<Encounter>> =
        encounterDao.getEncountersByCampaignId(campaignId)
            .map { list -> list.map { it.toDomain() } }

    suspend fun getById(id: Long): Flow<Encounter?> =
        encounterDao.getEncounterWithFightersAndConditions(id).map { it?.toDomain() }

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