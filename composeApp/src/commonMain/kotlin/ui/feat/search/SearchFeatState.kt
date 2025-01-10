package ui.feat.search

import androidx.compose.ui.text.input.TextFieldValue

data class SearchFeatState(
    val searchTextFieldValue: TextFieldValue = TextFieldValue(""),
    val isLoading: Boolean = false,
    val featList: List<FeatItem> = emptyList(),
) {

}