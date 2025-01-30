package ui.campaign.save

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.usecase.campaign.DeleteCampaignUseCase
import domain.usecase.campaign.GetCampaignByIdUseCase
import domain.usecase.campaign.SaveCampaignUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SaveCampaignViewModel(
    private val getCampaignByIdUseCase: GetCampaignByIdUseCase,
    private val saveCampaignUseCase: SaveCampaignUseCase,
    private val deleteCampaignUseCase: DeleteCampaignUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(SaveCampaignUiState())
    val state = _state.asStateFlow()

    suspend fun fetchCampaign(index: String) {
        val campaign = getCampaignByIdUseCase(index)
        _state.update {
            it.copy(
                id = campaign.uuid,
                name = TextFieldValue(campaign.name),
                description = TextFieldValue(campaign.description),
            )
        }
    }

    fun onNameChange(textFieldValue: TextFieldValue) {
        _state.update {
            it.copy(name = textFieldValue)
        }
    }

    fun onDescriptionChange(textFieldValue: TextFieldValue) {
        _state.update {
            it.copy(description = textFieldValue)
        }
    }

    fun saveCampaign(onSaved: () -> Unit) {
        viewModelScope.launch {
            val (id, name, description) = _state.value
            saveCampaignUseCase(id, name.text, description.text)
            onSaved()
        }
    }

    fun deleteCampaign(index: String, onDeleted: () -> Unit) {
        viewModelScope.launch {
            deleteCampaignUseCase(index)
            onDeleted()
        }
    }
}
