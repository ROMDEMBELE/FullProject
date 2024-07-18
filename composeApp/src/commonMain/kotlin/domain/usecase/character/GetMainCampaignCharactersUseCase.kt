package domain.usecase.character

import domain.model.character.Character
import domain.repository.CharacterRepository
import domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetMainCampaignCharactersUseCase(
    private val characterRepository: CharacterRepository,
    private val settingsRepository: SettingsRepository
) {

    operator fun invoke(): Flow<List<Character>> {
        return combine(
            settingsRepository.getMainCampaignId(),
            characterRepository.getAll()
        ) { id, list ->
            if (id != null) {
                list.filter { it.campaignId == id }
            } else {
                emptyList()
            }
        }

    }
}