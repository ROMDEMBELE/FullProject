package domain.usecase.campaign

import domain.repository.CampaignRepository
import domain.repository.CharacterRepository
import domain.repository.EncounterRepository

class DeleteCampaignUseCase(
    private val campaignRepository: CampaignRepository,
    private val characterRepository: CharacterRepository,
    private val encounterRepository: EncounterRepository,
) {

    suspend operator fun invoke(id: Long, force: Boolean = false) {

    }
}