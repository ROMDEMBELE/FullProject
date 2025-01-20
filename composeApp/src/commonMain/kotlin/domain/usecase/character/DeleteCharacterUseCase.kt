package domain.usecase.character

import domain.repository.CharacterRepository

class DeleteCharacterUseCase(private val characterRepository: CharacterRepository) {

    suspend operator fun invoke(index: String, force: Boolean = false) {
        // Check if the character can be deleted

        // Delete the character
        characterRepository.delete(index)
    }
}