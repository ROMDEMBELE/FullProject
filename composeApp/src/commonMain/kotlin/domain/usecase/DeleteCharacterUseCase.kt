package domain.usecase

import domain.repository.CharacterRepository

class DeleteCharacterUseCase(private val characterRepository: CharacterRepository) {

    fun execute(id: Long) {
        // check if character exists in database

        // check if character is in use in encounter

        // delete character
    }
}