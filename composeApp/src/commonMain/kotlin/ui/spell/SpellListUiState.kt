package ui.spell

import androidx.compose.ui.text.input.TextFieldValue
import domain.Level
import domain.spell.Spell

data class SpellListUiState(
    val spellsByLevel: Map<Level, List<Spell>> = emptyMap(),
    val filterByLevel: List<Level> = emptyList(),
    val textField: TextFieldValue = TextFieldValue(),
    val favoriteSpells: List<Spell> = emptyList()
) {
    val filterCounter: Int
        get() = filterByLevel.size

    val favoritesCounter: Int = favoriteSpells.size
}