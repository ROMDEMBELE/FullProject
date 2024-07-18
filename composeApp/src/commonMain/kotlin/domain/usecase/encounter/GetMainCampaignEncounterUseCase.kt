package domain.usecase.encounter

import domain.model.encounter.Encounter
import domain.repository.EncounterRepository
import domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetMainCampaignEncounterUseCase(
    private val settingsRepository: SettingsRepository,
    private val encounterRepository: EncounterRepository
) {

    operator fun invoke(): Flow<List<Encounter>> {
        return combine(
            settingsRepository.getMainCampaignId(),
            encounterRepository.getAll()
        ) { id, list -> if (id != null) list.filter { it.campaignId == id } else emptyList() }
    }
}