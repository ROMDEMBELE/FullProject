package domain.usecase.campaign

import domain.repository.CampaignRepository

class GetCampaignByIdUseCase(
    private val campaignRepository: CampaignRepository,
) {

    suspend operator fun invoke(index: String) = campaignRepository.getById(index)
}