package ui.campaign.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.model.Campaign
import domain.usecase.GetCampaignsUseCase
import domain.usecase.SaveCampaignUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CampaignMainViewModel(
    private val saveCampaignUseCase: SaveCampaignUseCase,
    private val getCampaignsUseCase: GetCampaignsUseCase,
) :
    ViewModel() {

    private val _uiState = MutableStateFlow(CampaignMainUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getCampaignsUseCase.execute().collectLatest { campaigns ->
                val campaignInProgress = campaigns.firstOrNull { it.inProgress }
                _uiState.update {
                    it.copy(
                        listOfCampaign = campaigns,
                        campaignInProgress = campaignInProgress,
                        isReady = true
                    )
                }
            }
        }
    }

    fun closeMainCampaign() {
        _uiState.value.campaignInProgress?.let {
            saveCampaignUseCase.execute(it.id, it.name, it.description, false)
        }
    }

    fun selectCampaignAsMain(campaign: Campaign) {
        saveCampaignUseCase.execute(campaign.id, campaign.name, campaign.description, true)
    }

}
