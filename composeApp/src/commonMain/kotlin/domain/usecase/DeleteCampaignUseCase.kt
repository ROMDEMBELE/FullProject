package domain.usecase

import domain.repository.CampaignRepository
import domain.repository.CharacterRepository
import domain.repository.SettingsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

class DeleteCampaignUseCase(
    private val campaignRepository: CampaignRepository,
    private val characterRepository: CharacterRepository,
    private val settingsRepository: SettingsRepository,
) {

    suspend fun execute(id: Long, force: Boolean = false) {
        // Check if campaign exist in database
        campaignRepository.getCampaignById(id).firstOrNull()
            ?: throw NullPointerException("Campaign with id $id does not exist")
        // Check if there is not combat in the campaign

        // Check if there is not character in the campaign
        val characters =
            characterRepository.getListOfCharacters().first().filter { it.campaignId == id }
        if (characters.isNotEmpty()) {
            if (force) {
                characters.forEach { characterRepository.deleteCharacter(it.id) }
            } else {
                throw IllegalStateException("Campaign is not empty, Please delete all related character before")
            }
        }

        // Delete id
        settingsRepository.setCurrentCampaignId(null)

        // Delete the campaign
        campaignRepository.deleteCampaign(id)
    }
}