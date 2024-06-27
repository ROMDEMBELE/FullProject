package domain.model

import domain.model.character.Character

data class Campaign(
    val id: Long,
    val name: String,
    val description: String,
    val inProgress: Boolean = false,
    val characters: List<Character> = emptyList()
)