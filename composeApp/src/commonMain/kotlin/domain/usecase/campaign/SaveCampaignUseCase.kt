package domain.usecase.campaign

import domain.model.Campaign
import domain.repository.CampaignRepository
import domain.repository.SettingsRepository
import kotlinx.coroutines.flow.firstOrNull

class SaveCampaignUseCase(
    private val campaignRepository: CampaignRepository,
    private val settingsRepository: SettingsRepository,
) {

    suspend operator fun invoke(
        id: Long?,
        name: String,
        description: String,
        isMain: Boolean = false
    ): Campaign? {
        if (name.isEmpty()) throw IllegalStateException("Name is empty")
        if (description.isEmpty()) throw IllegalStateException("Description is empty")

        val newId = campaignRepository.createOrUpdate(id, name, description)
            ?: throw IllegalStateException("Campaign not created nor updated")

        // Replace current main id
        if (isMain) settingsRepository.setMainCampaignId(newId)

        return campaignRepository.getById(newId).firstOrNull()
    }
}