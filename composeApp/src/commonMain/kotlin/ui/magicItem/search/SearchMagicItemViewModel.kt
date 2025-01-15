package ui.magicItem.search

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.model.magicItem.ItemRarity
import domain.model.magicItem.MagicItem
import domain.usecase.magicItem.AddMagicItemToFavoriteUseCase
import domain.usecase.magicItem.GetFavoritesMagicItemsUseCase
import domain.usecase.magicItem.RemoveMagicItemFromFavoriteUseCase
import domain.usecase.magicItem.SearchMagicItemUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchMagicItemViewModel(
    private val searchMagicItemUseCase: SearchMagicItemUseCase,
    private val getFavoritesMagicItemsUseCase: GetFavoritesMagicItemsUseCase,
    private val addMagicItemToFavoriteUseCase: AddMagicItemToFavoriteUseCase,
    private val removeMagicItemFromFavoriteUseCase: RemoveMagicItemFromFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SearchMagicItemUiState())
    val state: StateFlow<SearchMagicItemUiState> = _state.asStateFlow()

    private var searchJob: Job? = null

    private fun MagicItem.toSearchMagicItem(): SearchMagicItemItem = SearchMagicItemItem(
        key = key, name = name, isFavorite = isFavorite, rarity = rarity
    )

    init {
        fetchFavorites()

        fetchMagicItems(TextFieldValue(), null)
    }

    private fun fetchFavorites() {
        viewModelScope.launch {
            getFavoritesMagicItemsUseCase().collect { favorites ->
                _state.update {
                    it.copy(
                        favorites = favorites.map { item -> item.toSearchMagicItem() },
                        searchResult = it.searchResult.map { item ->
                            item.copy(isFavorite = favorites.any { favorite -> favorite.key == item.key })
                        },
                    )
                }
            }
        }
    }

    private fun fetchMagicItems(textField: TextFieldValue, rarity: ItemRarity?) {
        searchJob = viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            searchMagicItemUseCase(textField.text, rarity).collect { results ->
                _state.update {
                    it.copy(
                        searchResult = results.map { item -> item.toSearchMagicItem() },
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun searchMagicItems() {
        searchJob?.cancel()
        val textField = _state.value.searchTextFieldValue
        val rarity = _state.value.rarityFilter
        _state.update { it.copy(searchResult = emptyList(), isLoading = true) }
        fetchMagicItems(textField, rarity)
    }

    fun cancelSearch() {
        searchJob?.cancel()
        _state.update { it.copy(isLoading = false) }
    }

    fun onRarityFilterChange(range: ItemRarity? = null) {
        viewModelScope.launch {
            _state.update { it.copy(rarityFilter = range) }
            delay(500)
            searchMagicItems()
        }
    }

    fun onSearchTextFieldChange(textField: TextFieldValue) {
        viewModelScope.launch {
            _state.update { it.copy(searchTextFieldValue = textField) }
            delay(500)
            searchMagicItems()
        }
    }

    fun addToFavorite(key: String) {
        viewModelScope.launch {
            addMagicItemToFavoriteUseCase(key)
        }
    }

    fun removeFromFavorite(key: String) {
        viewModelScope.launch {
            removeMagicItemFromFavoriteUseCase(key)
        }
    }

}