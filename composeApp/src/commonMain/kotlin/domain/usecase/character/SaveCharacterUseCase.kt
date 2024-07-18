package domain.usecase.character

import androidx.compose.ui.text.input.TextFieldValue
import domain.model.Level
import domain.model.character.Background
import domain.model.character.Character
import domain.model.character.Species
import domain.repository.CharacterRepository
import domain.repository.SettingsRepository
import kotlinx.coroutines.flow.firstOrNull

class SaveCharacterUseCase(
    private val characterRepository: CharacterRepository,
    private val settingsRepository: SettingsRepository
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
        characterSpecies: Species,
        characterBackground: Background,
        characterClass: TextFieldValue
    ): Character? {
        require(playerName.text.isNotEmpty()) { "Player name cannot be empty" }
        require(characterName.text.isNotEmpty()) { "Character name cannot be empty" }
        require(armorClass >= 0) { "Armor class must be non-negative" }
        require(hitPoint >= 0) { "Hit point must be non-negative" }

        val campaignId = settingsRepository.getMainCampaignId().firstOrNull()
            ?: throw IllegalStateException("Character cannot be created without a campaign")

        val newId = characterRepository.createOrUpdateCharacter(
            id = id,
            fullName = characterName.text,
            campaignId = campaignId,
            player = playerName.text,
            level = level,
            armorClass = armorClass,
            hitPoint = hitPoint,
            spellSavingThrow = spellSave,
            charisma = charisma,
            dexterity = dexterity,
            constitution = constitution,
            intelligence = intelligence,
            wisdom = wisdom,
            strength = strength,
            speciesId = characterSpecies.id,
            backgroundId = characterBackground.id,
            characterClass = characterClass.text
        ) ?: throw NullPointerException("Unable to create or update character")

        return characterRepository.getById(newId).firstOrNull()
    }

}