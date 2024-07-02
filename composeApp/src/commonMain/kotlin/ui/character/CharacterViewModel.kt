package ui.character

import androidx.lifecycle.ViewModel
import domain.model.Campaign
import domain.usecase.GetCampaignCharacterUseCase
import domain.usecase.GetCampaignsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class CharacterViewModel(
    private val getCampaignCharacterUseCase: GetCampaignCharacterUseCase,
    private val getCampaignsUseCase: GetCampaignsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CharacterListUiState())
    val uiState: StateFlow<CharacterListUiState> = _uiState.asStateFlow()

    suspend fun fetchCampaign() {
        getCampaignsUseCase.execute().map { it.firstOrNull(Campaign::inProgress) }.firstOrNull()
            ?.let { campaign ->
                getCampaignCharacterUseCase.execute(campaign.id).collectLatest { characters ->
                    _uiState.update {
                        it.copy(
                            campaign = campaign,
                            characters = characters,
                            isReady = true
                        )
                    }
                }
            } ?: run { _uiState.update { it.copy(isReady = true) } }
    }
}