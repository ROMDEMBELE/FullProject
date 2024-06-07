package ui.spell

import androidx.compose.ui.text.input.TextFieldValue
import domain.Level
import domain.MagicSchool
import domain.Spell

data class SpellListUiState(
    val spellsByLevel: Map<Level, List<Spell>> = emptyMap(),
    val filterByLevel: List<Level> = emptyList(),
    val filterByMagicSchool: List<MagicSchool> = emptyList(),
    val textField: TextFieldValue = TextFieldValue(),
    val favorites: List<Spell> = emptyList()
) {
    val filterCounter: Int
        get() = filterByMagicSchool.size + filterByLevel.size

    val favoritesCounter: Int = favorites.size
}