package ui.character.search

import ui.campaign.search.AdventureItem

data class SearchCharacterUiState(
    private val charactersByCampaign: Map<AdventureItem, List<SearchCharacterItem>> = emptyMap(),
    val selectedCampaignKey: AdventureItem? = null,
    val isLoading: Boolean = false,
) {

    val campaigns: List<AdventureItem> = charactersByCampaign.keys.toList()

    val selectedCharacters: List<SearchCharacterItem>
        get() = charactersByCampaign[selectedCampaignKey] ?: emptyList()

}

