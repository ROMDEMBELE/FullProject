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


    }
}