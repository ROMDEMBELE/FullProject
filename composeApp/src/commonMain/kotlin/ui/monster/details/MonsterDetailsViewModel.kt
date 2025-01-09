package ui.monster.details

import androidx.lifecycle.ViewModel
import domain.usecase.monster.GetMonsterByKeyUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MonsterDetailsViewModel(
    private val getMonsterByKeyUseCase: GetMonsterByKeyUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MonsterDetailsUiState())
    val uiState = _uiState.asStateFlow()

    suspend fun fetchMonster(slug: String) {
        try {
            val monster = getMonsterByKeyUseCase(slug)
            _uiState.update {
                it.copy(
                    isReady = true,
                    key = monster.key,
                    name = monster.name,
                    challenge = monster.challenge,
                    isFavorite = monster.isFavorite,
                    size = monster.size,
                    type = monster.type,
                    alignment = monster.alignment,
                    armorsClass = monster.armorsClass,
                    hitPoints = monster.hitPoints,
                    walkSpeed = monster.walkSpeed,
                    swimSpeed = monster.swimSpeed,
                    flySpeed = monster.flySpeed,
                    burrowSpeed = monster.burrowSpeed,
                    climbSpeed = monster.climbSpeed,
                    hover = monster.hover,
                    charisma = monster.charisma,
                    charismaSave = monster.charismaSave,
                    dexterity = monster.dexterity,
                    dexteritySave = monster.dexteritySave,
                    constitution = monster.constitution,
                    constitutionSave = monster.constitutionSave,
                    intelligence = monster.intelligence,
                    intelligenceSave = monster.intelligenceSave,
                    strength = monster.strength,
                    strengthSave = monster.strengthSave,
                    wisdom = monster.wisdom,
                    damageImmunities = monster.damageImmunities,
                    damageResistances = monster.damageResistances,
                    damageVulnerabilities = monster.damageVulnerabilities,
                    conditionImmunities = monster.conditionImmunities,
                    passivePerception = monster.passivePerception,
                    darkVision = monster.darkVisionRange,
                    trueSight = monster.trueSightRange,
                    tremorSense = monster.tremorSenseRange,
                    blindSight = monster.blindSightRange,
                    languages = monster.languages,
                    trait = monster.traits,
                    actions = monster.actions,
                    bonusActions = monster.bonusActions,
                    reactions = monster.reactions,
                    legendaryActions = monster.legendaryActions,
                )
            }
        } catch (e: Exception) {
            _uiState.update { it.copy(isReady = true) }
        }
    }
}
