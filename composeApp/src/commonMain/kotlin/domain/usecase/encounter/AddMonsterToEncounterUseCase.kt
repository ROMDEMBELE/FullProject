package domain.usecase.encounter

import domain.repository.EncounterRepository
import domain.repository.MonsterRepository
import kotlinx.coroutines.flow.firstOrNull

class AddMonsterToEncounterUseCase(
    private val encounterRepository: EncounterRepository,
    private val monsterRepository: MonsterRepository
) {
    suspend fun execute(index: String, encounterId: Long) {
        
    }
}