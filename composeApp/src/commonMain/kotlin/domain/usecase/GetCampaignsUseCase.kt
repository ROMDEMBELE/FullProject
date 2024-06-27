package domain.usecase

import domain.model.Campaign
import domain.repository.CampaignRepository
import domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCampaignsUseCase(
    private val campaignRepository: CampaignRepository,
    private val settingsRepository: SettingsRepository
) {

    fun execute(): Flow<List<Campaign>> {
        return campaignRepository.getListOfCampaign().map { list ->
            val pendingId = settingsRepository.getCurrentCampaignId()
            list.map { campaign -> campaign.copy(inProgress = campaign.id == pendingId) }
        }
    }
}