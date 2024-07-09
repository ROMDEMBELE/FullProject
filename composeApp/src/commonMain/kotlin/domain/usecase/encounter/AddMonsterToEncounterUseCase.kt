package domain.usecase.encounter

import domain.repository.EncounterRepository
import domain.repository.MonsterRepository
import kotlinx.coroutines.flow.firstOrNull

class AddMonsterToEncounterUseCase(
    private val encounterRepository: EncounterRepository,
    private val monsterRepository: MonsterRepository
) {
    suspend fun execute(index: String, encounterId: Long) {
        encounterRepository.getById(encounterId).firstOrNull()
            ?: error("Encounter id: $encounterId not found")

        val monster = monsterRepository.getByIndex(index)
            ?: error("Monster with index:$index not found")

        encounterRepository.insertMonsterFighter(
            encounterId = encounterId,
            monster = monster
        )
    }
}