package ui.character.search

import domain.model.Alignment
import domain.model.Level
import domain.model.character.CharacterClass

data class SearchCharacterUiState(
    val selectedCampaignId: String? = null,
    val selectedCampaignName: String? = null,
    val characters: List<SearchCharacterItem> = emptyList(),
    val isReady: Boolean = false,
)

data class SearchCharacterItem(
    val id: String,
    val name: String,
    val level: Level,
    val alignment: Alignment,
    val characterClass: CharacterClass,
)