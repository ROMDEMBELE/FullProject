package domain.usecase.campaign

import domain.model.Campaign
import domain.repository.CampaignRepository
import domain.repository.CharacterRepository
import domain.repository.EncounterRepository
import domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetCampaignsUseCase(
    private val campaignRepository: CampaignRepository,
    private val encounterRepository: EncounterRepository,
    private val settingsRepository: SettingsRepository,
    private val characterRepository: CharacterRepository
) {

    operator fun invoke(): Flow<List<Campaign>> {
        return combine(
            settingsRepository.getMainCampaignId(),
            campaignRepository.getAll(),
            encounterRepository.getAll(),
            characterRepository.getAll()
        ) { mainCampaignId, campaigns, encounters, characters ->
            campaigns.map { campaign ->
                campaign.copy(
                    inProgress = campaign.id == mainCampaignId,
                    characters = characters.filter { it.campaignId == campaign.id },
                    encounters = encounters.filter { it.campaignId == campaign.id }
                )
            }
        }
    }
}