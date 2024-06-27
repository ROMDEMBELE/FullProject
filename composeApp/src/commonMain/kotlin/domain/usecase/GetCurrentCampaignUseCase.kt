package domain.usecase

import domain.model.Campaign
import domain.repository.CampaignRepository
import domain.repository.SettingsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

class GetCurrentCampaignUseCase(
    private val campaignRepository: CampaignRepository,
    private val settingsRepository: SettingsRepository
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun execute(): Flow<Campaign?> = settingsRepository.getCurrentCampaignId().flatMapLatest { id ->
        if (id == null) flowOf(null) else campaignRepository.getCampaignById(id)
    }
}