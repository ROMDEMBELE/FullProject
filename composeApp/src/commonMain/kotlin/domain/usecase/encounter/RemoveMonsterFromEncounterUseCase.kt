package domain.usecase.encounter

import domain.model.campaign.MonsterFighter
import domain.repository.EncounterRepository
import kotlinx.coroutines.flow.firstOrNull

class RemoveMonsterFromEncounterUseCase(
    private val encounterRepository: EncounterRepository,
) {
    suspend fun execute(index: String, encounterId: Long) {

    }
}