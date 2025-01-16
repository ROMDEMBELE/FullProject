package domain.usecase.character

import domain.model.character.Character
import domain.repository.CampaignRepository
import domain.repository.CharacterRepository
import domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class GetCharactersByCampaignIdUseCase(
    private val campaignRepository: CampaignRepository,
    private val characterRepository: CharacterRepository,
    private val settingsRepository: SettingsRepository
) {

    suspend operator fun invoke(campaignId: String): Flow<List<Character>> {
        val campaign = campaignRepository.getById(campaignId)

    }
}