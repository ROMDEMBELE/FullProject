package ui.monster.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.usecase.monster.AddMonsterToFavoriteUseCase
import domain.usecase.monster.GetFavoritesMonsterUseCase
import domain.usecase.monster.GetMonsterByKeyUseCase
import domain.usecase.monster.RemoveMonsterFromFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MonsterDetailsViewModel(
    val key: String,
    private val getMonsterByKeyUseCase: GetMonsterByKeyUseCase,
    private val getFavoritesMonsterUseCase: GetFavoritesMonsterUseCase,
    private val addMonsterToFavoritesUseCase: AddMonsterToFavoriteUseCase,
    private val removeMonsterFromFavoritesUseCase: RemoveMonsterFromFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MonsterDetailsUiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            fetchMonster(key)
        }
    }

    private suspend fun fetchMonster(key: String) {
        val monster = getMonsterByKeyUseCase(key)
        val isFavorite = getFavoritesMonsterUseCase().firstOrNull().orEmpty().any { it.key == key }
        _state.update {
            it.copy(
                isReady = true,
                key = monster.key,
                name = monster.name,
                challenge = monster.challenge,
                isFavorite = isFavorite,
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
    }

    fun addToFavorites() {
        viewModelScope.launch {
            addMonsterToFavoritesUseCase(key)
            _state.update {
                it.copy(isFavorite = true)
            }
        }
    }

    fun removeFromFavorites() {
        viewModelScope.launch {
            removeMonsterFromFavoritesUseCase(key)
            _state.update {
                it.copy(isFavorite = false)
            }
        }
    }
}
