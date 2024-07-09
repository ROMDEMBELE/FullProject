package ui.campaign.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.model.Campaign
import domain.usecase.campaign.GetMainCampaignUseCase
import domain.usecase.campaign.SaveCampaignUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CampaignMainViewModel(
    private val saveCampaign: SaveCampaignUseCase,
    private val getMainCampaign: GetMainCampaignUseCase
) :
    ViewModel() {

    private val _uiState = MutableStateFlow(CampaignMainUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getMainCampaign().collectLatest { campaignInProgress ->
                _uiState.update {
                    it.copy(
                        campaignInProgress = campaignInProgress,
                        isReady = true
                    )
                }
            }
        }
    }

    fun closeMainCampaign() {
        viewModelScope.launch {
            _uiState.value.campaignInProgress?.let {
                saveCampaign(it.id, it.name, it.description, false)
            }
        }
    }

    fun selectCampaignAsMain(campaign: Campaign) {
        viewModelScope.launch {
            saveCampaign(campaign.id, campaign.name, campaign.description, true)
        }
    }

}
