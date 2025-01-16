package ui.encounter

import domain.model.campaign.Campaign
import domain.model.campaign.Encounter

data class EncounterListUiState(
    val campaign: Campaign? = null,
    val encounters: List<Encounter> = emptyList(),
    val isReady: Boolean = false,
)