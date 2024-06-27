package domain.usecase

import domain.model.Ability
import domain.model.Level
import domain.model.character.Character
import domain.repository.CharacterRepository
import domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import ui.character.edit.EditCharacterUiState

class SaveCharacterUseCase(
    private val characterRepository: CharacterRepository,
    private val settingsRepository: SettingsRepository
) {

    suspend fun execute(uiState: EditCharacterUiState): Flow<Character?> {
        require(uiState.playerName.text.isNotEmpty()) { "Player name cannot be empty" }
        require(uiState.characterName.text.isNotEmpty()) { "Character name cannot be empty" }
        require(uiState.level in 1..20) { "Level must be between 1 and 20" }
        require(uiState.armorClass >= 0) { "Armor class must be non-negative" }
        require(uiState.hitPoint >= 0) { "Hit point must be non-negative" }
        require(uiState.abilities.all { it.value >= 0 }) { "Ability scores must be non-negative" }

        val campaignId = settingsRepository.getCurrentCampaignId().firstOrNull()
            ?: throw IllegalStateException("Character cannot be created without a campaign")

        val id = characterRepository.createOrUpdateCharacter(
            id = uiState.id,
            fullName = uiState.characterName.text,
            campaignId = campaignId,
            player = uiState.playerName.text,
            level = Level.fromInt(uiState.level),
            armorClass = uiState.armorClass,
            hitPoint = uiState.hitPoint,
            spellSavingThrow = uiState.spellSave,
            charisma = uiState.abilities[Ability.CHA]
                ?: error("Charisma ability score must not be null"),
            dexterity = uiState.abilities[Ability.DEX]
                ?: error("Dexterity ability score must not be null"),
            constitution = uiState.abilities[Ability.CON]
                ?: error("Constitution ability score must not be null"),
            intelligence = uiState.abilities[Ability.INT]
                ?: error("Intelligence ability score must not be null"),
            wisdom = uiState.abilities[Ability.WIS]
                ?: error("Wisdom ability score must not be null"),
            strength = uiState.abilities[Ability.STR]
                ?: error("Strength ability score must not be null"),
            speciesId = uiState.characterSpecies?.id
                ?: error("Character species must not be null"),
            backgroundId = uiState.characterBackground?.id
                ?: error("Character background must not be null"),
            characterClass = uiState.characterClass.text
        ) ?: throw NullPointerException("Unable to create or update character")

        return characterRepository.getCharacterById(id)
    }

}