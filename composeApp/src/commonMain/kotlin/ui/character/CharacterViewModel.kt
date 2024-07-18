package ui.character

import androidx.lifecycle.ViewModel
import domain.usecase.campaign.GetMainCampaignUseCase
import domain.usecase.character.GetMainCampaignCharactersUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update

class CharacterViewModel(
    private val getMainCampaignCharacter: GetMainCampaignCharactersUseCase,
    private val getMainCampaign: GetMainCampaignUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CharacterListUiState())
    val uiState: StateFlow<CharacterListUiState> = _uiState.asStateFlow()

    suspend fun fetchCampaign() {
        getMainCampaign().collectLatest { campaign ->
            delay(500)
            _uiState.update {
                it.copy(
                    campaign = campaign,
                )
            }
        }
        getMainCampaignCharacter().collectLatest { characters ->
            delay(500)
            _uiState.update {
                it.copy(
                    characters = characters,
                    isReady = true
                )
            }
        }
    }
}