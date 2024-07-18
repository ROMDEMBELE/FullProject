package ui.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.usecase.campaign.GetMainCampaignUseCase
import domain.usecase.character.GetMainCampaignCharactersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val getMainCampaignCharacter: GetMainCampaignCharactersUseCase,
    private val getMainCampaign: GetMainCampaignUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CharacterListUiState())
    val uiState: StateFlow<CharacterListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(getMainCampaign(), getMainCampaignCharacter()) { campaign, characters ->
                CharacterListUiState(
                    characters = characters,
                    campaign = campaign,
                    isReady = true
                )
            }.collectLatest {
                _uiState.value = it
            }
        }
    }
}