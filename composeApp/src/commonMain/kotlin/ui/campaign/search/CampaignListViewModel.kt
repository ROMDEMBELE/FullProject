package ui.campaign.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.usecase.campaign.GetAllCampaignsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CampaignListViewModel(
    private val getAllCampaignsUseCase: GetAllCampaignsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CampaignListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            fetchCampaigns()
        }
    }

    private suspend fun fetchCampaigns() {
        _uiState.update { it.copy(isLoading = true) }
        getAllCampaignsUseCase().collect { campaigns ->
            _uiState.update {
                it.copy(
                    listOfCampaign = campaigns.map { campaign ->
                        CampaignItem(campaign.uuid, campaign.name, campaign.description)
                    },
                    isLoading = false
                )
            }
        }
    }
}
