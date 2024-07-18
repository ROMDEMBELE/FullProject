package domain.model

import domain.model.character.Character
import domain.model.encounter.Encounter

data class Campaign(
    val id: Long,
    val name: String,
    val description: String,
    val inProgress: Boolean = false,
    val characters: List<Character> = emptyList(),
    val encounters: List<Encounter> = emptyList(),
)