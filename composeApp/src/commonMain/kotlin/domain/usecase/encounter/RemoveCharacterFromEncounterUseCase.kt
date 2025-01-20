package domain.usecase.encounter

import domain.model.campaign.CharacterFighter
import domain.repository.EncounterRepository

class RemoveCharacterFromEncounterUseCase(
    private val encounterRepository: EncounterRepository,
) {
    suspend fun execute(characterId: Long, encounterId: Long) {

    }
}