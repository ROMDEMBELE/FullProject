package ui.campaign.edit

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import domain.model.Campaign
import domain.usecase.DeleteCampaignUseCase
import domain.usecase.SaveCampaignUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EditCampaignViewModel(
    private val saveCampaignUseCase: SaveCampaignUseCase,
    private val deleteCampaignUseCase: DeleteCampaignUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditCampaignUiState())
    val uiState = _uiState.asStateFlow()

    fun setEdit(campaign: Campaign) {
        _uiState.update {
            it.copy(
                id = campaign.id,
                name = TextFieldValue(campaign.name),
                description = TextFieldValue(campaign.description),
            )
        }
    }

    fun saveCampaign() {
        val id = _uiState.value.id
        val name = _uiState.value.name.text
        val description = _uiState.value.description.text
        saveCampaignUseCase.execute(id, name, description, true)
    }

    suspend fun deleteCampaign(force: Boolean): Boolean {
        try {
            _uiState.value.id?.let { deleteCampaignUseCase.execute(it, force) }
            return true
        } catch (e: IllegalStateException) {
            _uiState.update { it.copy(error = e.message) }
            e.printStackTrace()
        } catch (e: NullPointerException) {
            _uiState.update { it.copy(error = e.message) }
            e.printStackTrace()
        }
        return false
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
}
