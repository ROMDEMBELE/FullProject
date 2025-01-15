package ui.campaign.main

import domain.Campaign

data class CampaignUiState(
    val listOfCampaign: List<Campaign> = emptyList(),
    val isReady: Boolean = false
)
