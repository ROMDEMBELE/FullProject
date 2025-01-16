package domain.usecase.campaign

import domain.model.campaign.Campaign
import domain.repository.CampaignRepository
import domain.repository.CharacterRepository
import domain.repository.EncounterRepository
import domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class GetCampaignsUseCase(
    private val campaignRepository: CampaignRepository,
    private val encounterRepository: EncounterRepository,
    private val settingsRepository: SettingsRepository,
    private val characterRepository: CharacterRepository
) {

    operator fun invoke(): Flow<List<Campaign>> {
        return emptyFlow()
    }
}