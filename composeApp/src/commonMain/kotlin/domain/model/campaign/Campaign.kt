package domain.model.campaign

import domain.model.character.Character

data class Campaign(
    val id: String,
    val name: String,
    val description: String,
    val characters: List<Character> = emptyList(),
    val encounters: List<Encounter> = emptyList(),
)