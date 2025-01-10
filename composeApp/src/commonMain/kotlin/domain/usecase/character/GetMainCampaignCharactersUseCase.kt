package domain.usecase.character

import domain.model.character.Character
import domain.repository.CharacterRepository
import domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class GetMainCampaignCharactersUseCase(
    private val characterRepository: CharacterRepository,
    private val settingsRepository: SettingsRepository
) {

    operator fun invoke(): Flow<List<Character>> {
        return emptyFlow()

    }
}