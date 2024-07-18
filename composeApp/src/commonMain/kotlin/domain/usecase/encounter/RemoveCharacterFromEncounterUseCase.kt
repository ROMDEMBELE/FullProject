package domain.usecase.encounter

import domain.model.encounter.CharacterFighter
import domain.repository.EncounterRepository
import kotlinx.coroutines.flow.firstOrNull

class RemoveCharacterFromEncounterUseCase(
    private val encounterRepository: EncounterRepository,
) {
    suspend fun execute(characterId: Long, encounterId: Long) {
        val encounter = encounterRepository.getById(encounterId).firstOrNull()
            ?: error("Encounter id: $encounterId not found")

        val characterFighter = encounter.fighters
            .filterIsInstance<CharacterFighter>()
            .firstOrNull { it.characterId == characterId }

        if (characterFighter == null) {
            throw IllegalArgumentException("Character id $characterId not part of the encounter")
        } else {
            encounterRepository.deleteCharacterFighter(characterFighter.id)
        }
    }
}