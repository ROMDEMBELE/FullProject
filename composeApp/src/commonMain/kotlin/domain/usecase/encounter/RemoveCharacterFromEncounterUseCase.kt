package domain.usecase.encounter

import domain.model.campaign.CharacterFighter
import domain.repository.EncounterRepository

class RemoveCharacterFromEncounterUseCase(
    private val encounterRepository: EncounterRepository,
) {
    suspend fun execute(characterId: Long, encounterId: Long) {
        val encounter = encounterRepository.getById(encounterId)

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