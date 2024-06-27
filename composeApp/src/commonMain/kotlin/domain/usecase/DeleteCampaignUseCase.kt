package domain.usecase

import domain.repository.CampaignRepository
import domain.repository.CharacterRepository
import kotlinx.coroutines.flow.firstOrNull

class DeleteCampaignUseCase(
    private val campaignRepository: CampaignRepository,
    private val characterRepository: CharacterRepository
) {

    suspend fun execute(id: Long, force: Boolean = false) {
        // Check if campaign exist in database
        campaignRepository.getCampaignById(id).firstOrNull() ?: throw NullPointerException("Campaign with id $id does not exist")
        // Check if there is not combat in the campaign

        // Check if there is not character in the campaign
        // Delete the campaign

    }
}