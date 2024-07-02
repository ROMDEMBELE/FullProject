package domain.usecase

import domain.model.character.Character
import domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCampaignCharacterUseCase(private val characterRepository: CharacterRepository) {

    fun execute(id: Long): Flow<List<Character>> =
        characterRepository.getListOfCharacters().map { list ->
            list.filter { it.campaignId == id }
        }
}