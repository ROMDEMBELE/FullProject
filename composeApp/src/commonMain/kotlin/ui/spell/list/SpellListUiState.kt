package ui.spell.list

import androidx.compose.ui.text.input.TextFieldValue
import domain.model.Level
import domain.model.spell.Spell

data class SpellListUiState(
    val spellList: List<Spell> = emptyList(),
    val filterByLevel: List<Level> = emptyList(),
    val textField: TextFieldValue = TextFieldValue(),
    val isReady: Boolean = false,
    val error: String? = null
) {
    val hasError: Boolean
        get() = !error.isNullOrEmpty()

    val favoriteByLevel: Map<Level, List<Spell>>
        get() = spellList.filter { spell -> spell.isFavorite }
            .sortedBy { spell -> spell.level }
            .groupBy { spell -> spell.level }

    val filteredSpellsByLevel: Map<Level, List<Spell>>
        get() = spellList.asSequence().sortedBy { spell -> spell.level }
            .filter { spell -> filterByLevel.isEmpty() || filterByLevel.contains(spell.level) }
            .filter { spell -> spell.name.contains(textField.text, true) }
            .sortedBy { spell -> spell.level }
            .groupBy { spell -> spell.level }

    val filterCounter: Int
        get() = filterByLevel.size

    val favoritesCounter: Int
        get() = favoriteByLevel.values.sumOf { it.size }

}