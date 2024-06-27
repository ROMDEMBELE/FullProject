package ui.settings.edit

import androidx.compose.ui.text.input.TextFieldValue

data class EditCampaignUiState(
    val id: Long? = null,
    val name: TextFieldValue = TextFieldValue(),
    val description: TextFieldValue = TextFieldValue(),
    val progress: Int = 1
) {
    val canBeDeleted: Boolean
        get() = id != null

    val isValid: Boolean
        get() = name.text.isNotEmpty() && description.text.isNotEmpty()
}
