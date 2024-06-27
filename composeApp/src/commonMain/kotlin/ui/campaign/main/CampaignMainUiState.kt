package ui.campaign.main

import domain.model.Campaign

data class CampaignMainUiState(
    val campaignInProgress: Campaign? = null,
    val listOfCampaign: List<Campaign> = emptyList(),
    val isReady: Boolean = false
)
