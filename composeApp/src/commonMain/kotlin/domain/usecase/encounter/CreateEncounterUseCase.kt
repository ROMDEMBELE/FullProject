package domain.usecase.encounter

import domain.repository.CampaignRepository
import domain.repository.EncounterRepository

class CreateEncounterUseCase(
    private val campaignRepository: CampaignRepository,
    private val encounterRepository: EncounterRepository
) {

    suspend fun execute(campaignId: Long, title: String, description: String) {
        campaignRepository.getById(campaignId)

    }
}