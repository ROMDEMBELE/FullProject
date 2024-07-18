package domain.usecase.encounter

import domain.repository.EncounterRepository
import kotlinx.coroutines.flow.firstOrNull

class UpdateEncounterUseCase(
    private val encounterRepository: EncounterRepository
) {

    suspend fun execute(
        id: Long,
        title: String,
        description: String,
        isFinished: Boolean,
        turn: Int
    ) {
        val entity = encounterRepository.getById(id).firstOrNull()
            ?: error("Encounter id: $id not found")

        // TODO check turn incrementation

        // TODO check is finished

        encounterRepository.updateEncounter(
            title = title,
            description = description,
            isFinished = isFinished,
            id = id,
            campaignId = entity.campaignId,
            turn = turn
        )

    }
}