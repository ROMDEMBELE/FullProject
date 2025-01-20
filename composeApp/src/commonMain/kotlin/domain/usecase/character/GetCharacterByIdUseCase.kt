package domain.usecase.character

import domain.model.character.Character
import domain.repository.CharacterRepository

class GetCharacterByIdUseCase(private val characterRepository: CharacterRepository) {

    suspend operator fun invoke(id: String): Character = characterRepository.getById(id)

}