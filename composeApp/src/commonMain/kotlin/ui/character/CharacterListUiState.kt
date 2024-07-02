package ui.character

import domain.model.Campaign
import domain.model.character.Character

data class CharacterListUiState(
    val campaign: Campaign? = null,
    val characters: List<Character> = emptyList(),
    val isReady: Boolean = false,
    val error: String? = null
)