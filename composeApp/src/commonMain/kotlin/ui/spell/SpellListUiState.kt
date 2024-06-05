package ui.spell

import androidx.compose.ui.text.input.TextFieldValue
import domain.Level
import domain.MagicSchool
import domain.Spell

data class SpellListUiState(
    val spellsByLevel: Map<Level, List<Spell>>,
    val filterByLevel: Map<Level, Boolean>,
    val filterByMagicSchool: Map<MagicSchool, Boolean>,
    var textField: TextFieldValue
) {
    val filterCounter: Int
        get() = filterByMagicSchool.values.count { it } + filterByLevel.values.count { it }
}