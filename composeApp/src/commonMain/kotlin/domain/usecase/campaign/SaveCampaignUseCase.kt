package domain.usecase.campaign

import domain.repository.CampaignRepository

class SaveCampaignUseCase(
    private val campaignRepository: CampaignRepository,
) {

    suspend operator fun invoke(
        id: String?,
        name: String,
        description: String,
    ) {
        campaignRepository.createOrUpdate(id, name, description)
    }
}