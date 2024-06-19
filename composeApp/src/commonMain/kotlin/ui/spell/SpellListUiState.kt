package ui.spell

import androidx.compose.ui.text.input.TextFieldValue
import domain.model.Level
import domain.model.spell.Spell

data class SpellListUiState(
    val filteredSpellsByLevel: Map<Level, List<Spell>> = emptyMap(),
    val filterByLevel: List<Level> = emptyList(),
    val textField: TextFieldValue = TextFieldValue(),
    val favoriteByLevel: Map<Level, List<Spell>> = emptyMap(),
) {
    val filterCounter: Int
        get() = filterByLevel.size

    val favoritesCounter: Int
        get() = favoriteByLevel.values.sumOf { it.size }
}