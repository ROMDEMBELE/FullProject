package domain.usecase.campaign

import domain.model.character.Character
import domain.repository.CharacterRepository
import domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMainCampaignCharactersUseCase(
    private val characterRepository: CharacterRepository,
    private val settingsRepository: SettingsRepository
) {

    operator fun invoke(): Flow<List<Character>> {
        return characterRepository.getListOfCharacters().map { list ->
            val id = settingsRepository.getMainCampaignId()
            if (id != null) list.filter { it.campaignId == id } else emptyList()
        }
    }
}