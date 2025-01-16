package domain.usecase.encounter

import domain.repository.CharacterRepository
import domain.repository.EncounterRepository

class AddCharacterToEncounterUseCase(
    private val encounterRepository: EncounterRepository,
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(characterId: Long, encounterId: Long) {
        val encounter = encounterRepository.getById(encounterId)

    }
}