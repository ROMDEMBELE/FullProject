package domain.usecase.campaign

import domain.model.campaign.Campaign
import domain.repository.CampaignRepository
import kotlinx.coroutines.flow.Flow

class GetAllCampaignsUseCase(
    private val campaignRepository: CampaignRepository,
) {

    operator fun invoke(): Flow<List<Campaign>> = campaignRepository.getAll()
}