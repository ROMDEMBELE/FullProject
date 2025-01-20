package ui.character.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.usecase.campaign.GetAllCampaignsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ui.campaign.search.AdventureItem

class SearchCharacterViewModel(
    private val getAllCampaignsUseCase: GetAllCampaignsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchCharacterUiState())
    val uiState: StateFlow<SearchCharacterUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            fetchAllCampaigns()
        }
    }

    private suspend fun fetchAllCampaigns() {
        _uiState.update { it.copy(isLoading = true) }
        getAllCampaignsUseCase().collect { campaigns ->
            _uiState.update {
                it.copy(
                    charactersByCampaign = buildMap {
                        campaigns.forEach { campaign ->
                            val key = AdventureItem(
                                id = campaign.uuid,
                                title = campaign.name,
                                description = campaign.description
                            )

                            val characters = campaign.characters.map { character ->
                                SearchCharacterItem(
                                    id = character.uuid,
                                    name = character.name,
                                    level = character.level,
                                    alignment = character.alignment,
                                    characterClass = character.characterClass,
                                )
                            }
                            put(key, characters)
                        }
                    },
                    isLoading = false
                )
            }
        }
    }

    fun onCampaignSelected(key: AdventureItem?) {
        _uiState.update { it.copy(selectedCampaignKey = key) }
    }
}