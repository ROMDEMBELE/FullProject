package domain.usecase

import domain.repository.CampaignRepository
import domain.repository.SettingsRepository

class SaveCampaignUseCase(
    private val campaignRepository: CampaignRepository,
    private val settingsRepository: SettingsRepository,
) {

    fun execute(
        id: Long?,
        name: String,
        description: String,
        isMain: Boolean = false
    ) {
        if (name.isEmpty()) throw IllegalStateException("Name is empty")
        if (description.isEmpty()) throw IllegalStateException("Description is empty")

        val newId = campaignRepository.createOrUpdateCampaign(id, name, description)
            ?: throw IllegalStateException("Campaign not created nor updated")

        if (isMain) settingsRepository.setCurrentCampaignId(newId)
        else settingsRepository.setCurrentCampaignId(null)
    }
}