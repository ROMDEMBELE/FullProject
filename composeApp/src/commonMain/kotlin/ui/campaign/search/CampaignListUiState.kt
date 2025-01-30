package ui.campaign.search

data class CampaignListUiState(
    val listOfCampaign: List<CampaignItem> = emptyList(),
    val isLoading: Boolean = false
)
