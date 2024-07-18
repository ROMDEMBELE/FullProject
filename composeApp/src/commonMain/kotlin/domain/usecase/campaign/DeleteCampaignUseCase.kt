package domain.usecase.campaign

import domain.repository.CampaignRepository
import domain.repository.CharacterRepository
import domain.repository.EncounterRepository
import domain.repository.SettingsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

class DeleteCampaignUseCase(
    private val campaignRepository: CampaignRepository,
    private val characterRepository: CharacterRepository,
    private val settingsRepository: SettingsRepository,
    private val encounterRepository: EncounterRepository,
) {

    suspend operator fun invoke(id: Long, force: Boolean = false): Boolean {
        // Check if campaign exist in database
        campaignRepository.getById(id).firstOrNull()
            ?: throw NullPointerException("Campaign with id $id does not exist")
        // Check if there is not combat in the campaign

        // Check if there is not character in the campaign
        val characters =
            characterRepository.getByCampaignId(id).first()
        if (characters.isNotEmpty()) {
            if (force) {
                characters.forEach { characterRepository.deleteCharacter(it.id) }
            } else {
                throw IllegalStateException("Campaign is not empty, Please delete all related character before")
            }
        }

        val encounters = encounterRepository.getByCampaignId(id).first()
        if (encounters.isNotEmpty()) {
            if (force) {
                encounters.forEach { encounterRepository.delete(it.id) }
            } else {
                throw IllegalStateException("Campaign is not empty, Please delete all related encounters before")
            }
        }


        // Delete id
        settingsRepository.setMainCampaignId(null)

        // Delete the campaign
        campaignRepository.delete(id)
        return true
    }
}