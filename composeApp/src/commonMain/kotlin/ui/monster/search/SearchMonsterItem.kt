package ui.monster.search

import domain.model.monster.Challenge
import domain.model.monster.CreatureType
import kotlinx.serialization.Serializable

/**
 * Item of the monster list to display in the search screen.
 *
 * @property key The key of the monster.
 * @property name The name of the monster.
 * @property isFavorite True if the monster is a favorite, false otherwise.
 * @property challenge The challenge of the monster.
 * @property type The type of the monster.
 */
@Serializable
data class SearchMonsterItem(
    val key: String,
    val name: String,
    val isFavorite: Boolean,
    val challenge: Challenge,
    val type: CreatureType
)
