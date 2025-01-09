package ui.spell.search

import androidx.compose.ui.text.input.TextFieldValue
import domain.model.Level

data class SpellListUiState(
    val favorites: List<SearchSpellItem> = emptyList(),
    val spellList: List<SearchSpellItem> = emptyList(),
    val filterByLevel: Map<Level, Boolean> = Level.entries.subList(0, 10).associateWith { true },
    val textField: TextFieldValue = TextFieldValue(),
    val isLoading: Boolean = false,
    val showFavorites: Boolean = false,
    val error: String? = null
) {

    val favoriteByLevel: Map<Level, List<SearchSpellItem>>
        get() = favorites
            .sortedBy { spell -> spell.level }
            .groupBy { spell -> spell.level }

    val filteredSpellsByLevel: Map<Level, List<SearchSpellItem>>
        get() = spellList.asSequence().sortedBy { spell -> spell.level }
            .filter { spell -> filterByLevel[spell.level] ?: false }
            .filter { spell -> spell.name.contains(textField.text, true) }
            .sortedBy { spell -> spell.level }
            .groupBy { spell -> spell.level }

    val filterCounter: Int
        get() = filterByLevel.count { !it.value }

    val favoritesCounter: Int
        get() = favoriteByLevel.values.sumOf { it.size }

}