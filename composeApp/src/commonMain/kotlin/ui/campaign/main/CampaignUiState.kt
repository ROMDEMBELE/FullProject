package ui.campaign.main

import domain.model.campaign.Campaign

data class CampaignUiState(
    val listOfCampaign: List<Campaign> = emptyList(),
    val isReady: Boolean = false
)
