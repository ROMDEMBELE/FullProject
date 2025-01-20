package ui.campaign.search

data class AdventurePagerUiState(
    val listOfCampaign: List<AdventureItem> = emptyList(),
    val isLoading: Boolean = false
)
