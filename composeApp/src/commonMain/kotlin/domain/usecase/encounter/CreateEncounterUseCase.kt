package domain.usecase.encounter

import domain.repository.CampaignRepository
import domain.repository.EncounterRepository
import kotlinx.coroutines.flow.firstOrNull

class CreateEncounterUseCase(
    private val campaignRepository: CampaignRepository,
    private val encounterRepository: EncounterRepository
) {

    suspend fun execute(campaignId: Long, title: String, description: String) {
        campaignRepository.getById(campaignId).firstOrNull()
            ?: error("Campaign id $campaignId not found")

        encounterRepository.insertEncounter(
            campaignId = campaignId,
            title = title,
            description = description,
        )
    }
}