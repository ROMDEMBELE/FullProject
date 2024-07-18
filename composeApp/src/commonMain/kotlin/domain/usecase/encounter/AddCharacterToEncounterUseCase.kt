package domain.usecase.encounter

import domain.model.encounter.CharacterFighter
import domain.repository.CharacterRepository
import domain.repository.EncounterRepository
import kotlinx.coroutines.flow.firstOrNull

class AddCharacterToEncounterUseCase(
    private val encounterRepository: EncounterRepository,
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(characterId: Long, encounterId: Long) {
        val encounter = encounterRepository.getById(encounterId).firstOrNull()
            ?: error("Encounter id: $encounterId not found")

        if (encounter.fighters.filterIsInstance<CharacterFighter>()
                .any { it.characterId == characterId }
        ) {
            throw IllegalArgumentException("Character id $characterId already part of the encounter")
        }

        val character = characterRepository.getById(characterId).firstOrNull()
            ?: error("Character id $characterId not found")

        encounterRepository.insertCharacterFighter(
            encounterId = encounterId,
            character = character
        )
    }
}