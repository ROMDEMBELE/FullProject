package domain.usecase.encounter

import domain.model.encounter.Encounter
import domain.repository.EncounterRepository
import domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GetMainCampaignEncounterUseCase(
    private val settingsRepository: SettingsRepository,
    private val encounterRepository: EncounterRepository
) {

    suspend operator fun invoke(): Flow<List<Encounter>> {
        val campaignId = settingsRepository.getMainCampaignId()
        return if (campaignId == null) {
            flowOf(emptyList())
        } else {
            encounterRepository.getByCampaignId(campaignId)
        }
    }
}