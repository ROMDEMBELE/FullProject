package ui.character

import domain.model.character.Character

data class CharacterListUiState(
    val characters: List<Character> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)