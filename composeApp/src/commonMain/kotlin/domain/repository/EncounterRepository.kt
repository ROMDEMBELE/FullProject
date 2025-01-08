package domain.repository

import data.database.room.entity.CharacterFighterEntity
import domain.model.character.Character
import domain.model.encounter.Encounter
import domain.model.monster.Monster
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class EncounterRepository(
) {



    fun getByCampaignId(campaignId: Long): Flow<List<Encounter>> = emptyFlow()

    fun getById(id: Long): Flow<Encounter?> = emptyFlow()

    fun getAll(): Flow<List<Encounter>> = emptyFlow()

    suspend fun insertEncounter(campaignId: Long, title: String, description: String) {

    }

    suspend fun updateEncounter(
        id: Long,
        campaignId: Long,
        title: String,
        description: String,
        turn: Int,
        isFinished: Boolean
    ) {

    }

    suspend fun updateFighter(fighter: CharacterFighterEntity) {
    }

    suspend fun insertCharacterFighter(
        encounterId: Long,
        character: Character
    ): Long {
       return 1L
    }

    suspend fun insertMonsterFighter(
        encounterId: Long,
        monster: Monster,
    ) {

    }

    suspend fun delete(id: Long) {
    }

    suspend fun deleteCharacterFighter(id: Long): Int = 1

    suspend fun deleteMonsterFighter(id: Long): Int = 1

}