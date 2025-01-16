package domain.usecase.encounter

import domain.model.campaign.Encounter
import domain.repository.EncounterRepository

class DeleteEncounterUseCase(private val encounterRepository: EncounterRepository) {

    suspend operator fun invoke(encounter: Encounter, force: Boolean = false) {

    }
}