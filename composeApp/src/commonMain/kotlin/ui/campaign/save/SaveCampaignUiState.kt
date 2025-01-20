package ui.campaign.save

import androidx.compose.ui.text.input.TextFieldValue

data class SaveCampaignUiState(
    val id: String? = null,
    val name: TextFieldValue = TextFieldValue(),
    val description: TextFieldValue = TextFieldValue(),
    val isReady: Boolean = false,
    val showDeleteButton: Boolean = false
) {

    val isValid: Boolean
        get() = name.text.isNotEmpty() && description.text.isNotEmpty()

}
