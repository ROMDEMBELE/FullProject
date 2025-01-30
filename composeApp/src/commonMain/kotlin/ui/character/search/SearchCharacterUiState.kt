package ui.character.search

import ui.campaign.search.CampaignItem

data class SearchCharacterUiState(
    private val charactersByCampaign: Map<CampaignItem, List<SearchCharacterItem>> = emptyMap(),
    val selectedCampaignKey: CampaignItem? = null,
    val isLoading: Boolean = false,
) {

    val campaigns: List<CampaignItem> = charactersByCampaign.keys.toList()

    val selectedCharacters: List<SearchCharacterItem>
        get() = charactersByCampaign[selectedCampaignKey] ?: emptyList()

}

