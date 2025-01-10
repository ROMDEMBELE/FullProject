package ui.feat.search

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.model.Feat
import domain.usecase.feat.SearchFeatUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchFeatViewModel(private val searchFeatUseCase: SearchFeatUseCase) : ViewModel() {

    private val _state = MutableStateFlow(SearchFeatState())
    val state = _state.asStateFlow()

    private var searchJob: Job? = null

    init {
        searchFeat(TextFieldValue())
    }

    private fun Feat.toItem() = FeatItem(
        key = this.key,
        name = this.name,
        benefits = this.benefits,
        hasPrerequisites = this.hasPrerequisites,
        prerequisites = this.prerequisite
    )

    private fun searchFeat(textFieldValue: TextFieldValue) {
        searchJob?.cancel()
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            searchFeatUseCase(textFieldValue).collect {
                _state.value = _state.value.copy(
                    isLoading = false,
                    featList = it.map { feat -> feat.toItem() }
                )
            }
        }
    }

    fun onSearchTextChange(text: TextFieldValue) {
        _state.value = _state.value.copy(searchTextFieldValue = text)
        viewModelScope.launch {
            delay(500)
            searchFeat(text)
        }
    }
}
