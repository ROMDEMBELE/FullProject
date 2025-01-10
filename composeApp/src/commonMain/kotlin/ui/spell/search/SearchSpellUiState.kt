package ui.spell.search

import androidx.compose.ui.text.input.TextFieldValue
import domain.model.Level

data class SearchSpellUiState(
    val favorites: List<SearchSpellItem> = emptyList(),
    val searchResult: List<SearchSpellItem> = emptyList(),
    val filterLevelRange: ClosedFloatingPointRange<Float> = Level.LEVEL_0.ordinal.toFloat()..Level.LEVEL_9.ordinal.toFloat(),
    val searchTextField: TextFieldValue = TextFieldValue(),
    val isLoading: Boolean = false,
    val showFavorites: Boolean = false,
    val error: String? = null,
) {

    val favoriteByLevel: Map<Level, List<SearchSpellItem>>
        get() = favorites
            .sortedBy { spell -> spell.level }
            .groupBy { spell -> spell.level }

    val spellByLevel: Map<Level, List<SearchSpellItem>>
        get() = searchResult
            .sortedBy { spell -> spell.level }
            .groupBy { spell -> spell.level }

    val resultCounter: Int
        get() = searchResult.size

    val favoritesCounter: Int
        get() = favorites.size

    val minLevel: Level
        get() = Level.entries[filterLevelRange.start.toInt()]

    val maxLevel: Level
        get() = Level.entries[filterLevelRange.endInclusive.toInt()]

    val levelRange: ClosedFloatingPointRange<Float> =
        Level.LEVEL_0.ordinal.toFloat()..Level.LEVEL_9.ordinal.toFloat()

}