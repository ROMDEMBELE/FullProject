package ui.encounter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.usecase.campaign.GetMainCampaignUseCase
import domain.usecase.encounter.AddCharacterToEncounterUseCase
import domain.usecase.encounter.AddMonsterToEncounterUseCase
import domain.usecase.encounter.CreateEncounterUseCase
import domain.usecase.encounter.GetMainCampaignEncounterUseCase
import domain.usecase.encounter.RemoveCharacterFromEncounterUseCase
import domain.usecase.encounter.RemoveMonsterFromEncounterUseCase
import domain.usecase.encounter.UpdateEncounterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EncounterListViewModel(
    private val createEncounter: CreateEncounterUseCase,
    private val addCharacter: AddCharacterToEncounterUseCase,
    private val addMonster: AddMonsterToEncounterUseCase,
    private val removeCharacter: RemoveCharacterFromEncounterUseCase,
    private val removeMonster: RemoveMonsterFromEncounterUseCase,
    private val updateEncounter: UpdateEncounterUseCase,
    private val getMainCampaign: GetMainCampaignUseCase,
    private val getEncounters: GetMainCampaignEncounterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EncounterListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getMainCampaign().collectLatest { campaign ->
                _uiState.update {
                    it.copy(campaign = campaign)
                }
            }

            getEncounters().collectLatest { encounters ->
                _uiState.update {
                    it.copy(encounters = encounters, isReady = true)
                }
            }
        }
    }

}