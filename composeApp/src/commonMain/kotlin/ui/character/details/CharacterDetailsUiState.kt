package ui.character.details

import domain.model.Ability
import domain.model.Level
import domain.model.character.CharacterClass

data class CharacterDetailsUiState(
    val isReady: Boolean = false,
    val characterName: String = "",
    val characterClass: CharacterClass = CharacterClass.BARBARIAN,
    val characterLevel: Level = Level.LEVEL_0,
    val armorClass: Int = 0,
    val hitPoint: Int = 0,
    val perceptionPassive: Int = 0,
    val abilities: Map<Ability, Int> = emptyMap(),
    val canBeDeleted: Boolean = false
)
