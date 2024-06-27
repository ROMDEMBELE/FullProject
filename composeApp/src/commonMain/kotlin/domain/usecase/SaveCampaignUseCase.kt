package domain.usecase

import domain.model.Campaign
import domain.repository.CampaignRepository
import kotlinx.coroutines.flow.Flow
import ui.settings.edit.EditCampaignUiState

class SaveCampaignUseCase(
    private val campaignRepository: CampaignRepository,
) {

    fun execute(uiState: EditCampaignUiState): Flow<Campaign?> {
        val id = uiState.id
        val name = uiState.name.text
        val description = uiState.description.text
        val progress = uiState.progress
        if (name.isEmpty()) throw IllegalStateException("Name is empty")
        if (description.isEmpty()) throw IllegalStateException("Description is empty")
        if (progress < 0 || progress > 100) throw IllegalStateException("Progress must be between 0 and 100")

        val newId = campaignRepository.createOrUpdateCampaign(id, name, description, progress)
            ?: throw IllegalStateException("Campaign not created nor updated")

        return campaignRepository.getCampaignById(newId)
    }
}