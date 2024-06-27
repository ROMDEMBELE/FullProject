package ui.settings.edit

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import domain.repository.CampaignRepository
import domain.usecase.SaveCampaignUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update

class EditCampaignViewModel(
    private val campaignRepository: CampaignRepository,
    private val saveCampaignUseCase: SaveCampaignUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(EditCampaignUiState())
    val uiState = _uiState.asStateFlow()

    suspend fun loadCampaign(id: Long) {
        campaignRepository.getCampaignById(id).filterNotNull().first().let { campaign ->
            _uiState.update {
                it.copy(
                    id = campaign.id,
                    name = TextFieldValue(campaign.name),
                    description = TextFieldValue(campaign.description),
                    progress = campaign.progress,
                )
            }

        }
    }

    fun saveCampaign() {
        saveCampaignUseCase.execute(_uiState.value)
    }

    fun updateName(textFieldValue: TextFieldValue) {
        _uiState.update {
            it.copy(name = textFieldValue)
        }
    }

    fun updateDescription(textFieldValue: TextFieldValue) {
        _uiState.update {
            it.copy(description = textFieldValue)
        }
    }

    fun updateProgress(progress: Int) {
        _uiState.update {
            it.copy(progress = progress)
        }
    }

}
