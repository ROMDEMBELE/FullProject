package domain.usecase.character

import androidx.compose.ui.text.input.TextFieldValue
import domain.model.Level
import domain.repository.CharacterRepository

class SaveCharacterUseCase(
    private val characterRepository: CharacterRepository,
) {

    suspend operator fun invoke(
        id: Long? = null,
        playerName: TextFieldValue,
        characterName: TextFieldValue,
        level: Level,
        armorClass: Int,
        hitPoint: Int,
        charisma: Int,
        dexterity: Int,
        constitution: Int,
        intelligence: Int,
        wisdom: Int,
        strength: Int,
        spellSave: Int,
        characterClass: TextFieldValue
    ) {
    }

}