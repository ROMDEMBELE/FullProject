package ui.magicItem.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.usecase.magicItem.AddMagicItemToFavoriteUseCase
import domain.usecase.magicItem.GetMagicItemByKeyUseCase
import domain.usecase.magicItem.RemoveMagicItemFromFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MagicItemDetailsViewModel(
    private val index: String,
    private val getMagicItemByKeyUseCase: GetMagicItemByKeyUseCase,
    private val addMagicItemToFavoriteUseCase: AddMagicItemToFavoriteUseCase,
    private val removeMagicItemFromFavoriteUseCase: RemoveMagicItemFromFavoriteUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MagicItemDetailsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            fetchMagicItem(index)
        }
    }

    suspend fun fetchMagicItem(index: String) {
        val magicItem = getMagicItemByKeyUseCase(index)
        _uiState.value = _uiState.value.copy(
            key = magicItem.key,
            isFavorite = magicItem.isFavorite,
            isMagical = magicItem.isMagical,
            name = magicItem.name,
            description = magicItem.description,
            cost = magicItem.cost,
            weight = magicItem.weight,
            requireAttunement = magicItem.requireAttunement,
            category = magicItem.category,
            rarity = magicItem.rarity,
            isReady = true
        )
    }
}