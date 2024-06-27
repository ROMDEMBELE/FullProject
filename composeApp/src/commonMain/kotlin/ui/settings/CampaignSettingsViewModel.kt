package ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.usecase.GetCurrentCampaignUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CampaignSettingsViewModel(private val getCurrentCampaignUseCase: GetCurrentCampaignUseCase) :
    ViewModel() {

    private val _uiState = MutableStateFlow(CampaignSettingsUiState(0))
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getCurrentCampaignUseCase.execute().collectLatest {
                _uiState.update {
                    it.copy(
                        id = it.id,
                        fullName = it.fullName,
                        description = it.description
                    )
                }
            }
        }
    }

}
