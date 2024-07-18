package domain.usecase.campaign

import domain.model.Campaign
import domain.repository.CampaignRepository
import domain.repository.SettingsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

class GetMainCampaignUseCase(
    private val campaignRepository: CampaignRepository,
    private val settingsRepository: SettingsRepository
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<Campaign?> {
        return settingsRepository.getMainCampaignId().flatMapLatest { id ->
            if (id != null) campaignRepository.getById(id) else flowOf(null)
        }
    }
}