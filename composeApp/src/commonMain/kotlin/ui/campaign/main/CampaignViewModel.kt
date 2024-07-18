package ui.campaign.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.model.Campaign
import domain.repository.SettingsRepository
import domain.usecase.campaign.GetCampaignsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CampaignViewModel(
    private val getCampaigns: GetCampaignsUseCase,
    private val settingsRepository: SettingsRepository
) :
    ViewModel() {

    private val _uiState = MutableStateFlow(CampaignUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getCampaigns().collectLatest { campaigns ->
                campaigns.sortedBy { it.inProgress }
                _uiState.update {
                    it.copy(listOfCampaign = campaigns, isReady = true)
                }
            }
        }
    }

    fun playCampaign(campaign: Campaign) {
        viewModelScope.launch {
            settingsRepository.setMainCampaignId(campaign.id)
        }
    }

    fun stopCampaign(campaign: Campaign) {
        viewModelScope.launch {
            settingsRepository.setMainCampaignId(null)
        }
    }
}
