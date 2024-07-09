package domain.usecase.campaign

import domain.model.Campaign
import domain.repository.CampaignRepository
import domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GetMainCampaignUseCase(
    private val campaignRepository: CampaignRepository,
    private val settingsRepository: SettingsRepository
) {

    operator fun invoke(): Flow<Campaign?> {
        return settingsRepository.getMainCampaignId()?.let { id ->
            campaignRepository.getById(id)
        } ?: flowOf(null)
    }
}