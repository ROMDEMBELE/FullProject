package domain.usecase.character

import domain.repository.CharacterRepository
import kotlinx.coroutines.flow.firstOrNull

class DeleteCharacterUseCase(private val characterRepository: CharacterRepository) {

    suspend operator fun invoke(id: Long, force: Boolean = false): Boolean {
        // check if character exists in database
        characterRepository.getCharacterById(id).firstOrNull()
            ?: error("Character with id $id does not exist")
        // TODO check if character is in use in encounter

        // delete character
        characterRepository.deleteCharacter(id)

        return true
    }
}