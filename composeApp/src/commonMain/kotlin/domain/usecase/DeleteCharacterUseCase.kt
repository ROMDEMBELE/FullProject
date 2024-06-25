package domain.usecase

import domain.repository.CharacterRepository
import kotlinx.coroutines.flow.firstOrNull

class DeleteCharacterUseCase(private val characterRepository: CharacterRepository) {

    suspend fun execute(id: Long) {
        // check if character exists in database
        val character = characterRepository.getCharacterById(id).firstOrNull() ?: error("Character with id $id does not exist")
        // check if character is in use in encounter

        // delete character
        characterRepository.deleteCharacter(id)
    }
}