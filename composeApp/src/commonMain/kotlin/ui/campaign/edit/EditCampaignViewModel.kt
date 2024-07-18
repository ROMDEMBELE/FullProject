package ui.campaign.edit

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import domain.model.Campaign
import domain.usecase.campaign.DeleteCampaignUseCase
import domain.usecase.campaign.SaveCampaignUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EditCampaignViewModel(
    private val saveCampaign: SaveCampaignUseCase,
    private val deleteCampaign: DeleteCampaignUseCase
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

    suspend fun save() {
        val (id, name, description) = _uiState.value
        saveCampaign(id, name.text, description.text, false)
    }

    suspend fun deleteCampaign(force: Boolean): Boolean {
        try {
            _uiState.value.id?.let { deleteCampaign(it, force) }
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
