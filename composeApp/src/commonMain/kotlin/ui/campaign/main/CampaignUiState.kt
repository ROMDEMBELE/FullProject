package ui.campaign.main

import domain.model.Campaign

data class CampaignUiState(
    val listOfCampaign: List<Campaign> = emptyList(),
    val isReady: Boolean = false
)
