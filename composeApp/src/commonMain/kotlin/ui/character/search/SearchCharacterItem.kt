package ui.character.search

import domain.model.Alignment
import domain.model.Level
import domain.model.character.CharacterClass

/**
 * @author Romain Dembele
 *
 * @property id The unique identifier of the character
 * @property name The name of the character
 * @property level The level of the character
 * @property alignment The alignment of the character
 * @property characterClass The class of the character
 */
data class SearchCharacterItem(
    val id: String,
    val name: String,
    val level: Level,
    val alignment: Alignment,
    val characterClass: CharacterClass,
)