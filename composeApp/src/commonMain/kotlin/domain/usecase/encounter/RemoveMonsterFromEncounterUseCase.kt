package domain.usecase.encounter

import domain.model.encounter.MonsterFighter
import domain.repository.EncounterRepository
import kotlinx.coroutines.flow.firstOrNull

class RemoveMonsterFromEncounterUseCase(
    private val encounterRepository: EncounterRepository,
) {
    suspend fun execute(index: String, encounterId: Long) {
        val encounter = encounterRepository.getById(encounterId).firstOrNull()
            ?: error("Encounter id: $encounterId not found")

        val entity = encounter.fighters
            .filterIsInstance<MonsterFighter>()
            .firstOrNull { it.index == index }

        if (entity == null) {
            throw IllegalArgumentException("Monster index $index not part of the encounter")
        } else {
            encounterRepository.deleteMonsterFighter(entity.id)
        }
    }
}