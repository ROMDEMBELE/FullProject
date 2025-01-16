package domain.usecase.character

import domain.repository.CharacterRepository

class DeleteCharacterUseCase(private val characterRepository: CharacterRepository) {

    suspend operator fun invoke(id: Long, force: Boolean = false) {

    }
}