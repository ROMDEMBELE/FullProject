package ui.campaign.edit

import androidx.compose.ui.text.input.TextFieldValue

data class EditCampaignUiState(
    val id: Long? = null,
    val name: TextFieldValue = TextFieldValue(),
    val description: TextFieldValue = TextFieldValue(),
    val error: String? = null,
) {

    val isValid: Boolean
        get() = name.text.isNotEmpty() && description.text.isNotEmpty()
}
