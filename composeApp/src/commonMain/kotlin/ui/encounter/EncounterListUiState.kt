package ui.encounter

import domain.model.Campaign
import domain.model.encounter.Encounter

data class EncounterListUiState(
    val campaign: Campaign? = null,
    val encounters: List<Encounter> = emptyList(),
    val isReady: Boolean = false,
)