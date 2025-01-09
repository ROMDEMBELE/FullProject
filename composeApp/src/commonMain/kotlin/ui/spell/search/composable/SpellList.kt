package ui.spell.search.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import domain.model.Level
import ui.color
import ui.composable.CustomLazyHeaderList
import ui.composable.MediumBoldDarkBlue
import ui.composable.darkBlue
import ui.spell.search.SearchSpellItem

@Composable
fun SpellList(
    spellByLevel: Map<Level, List<SearchSpellItem>>,
    onFavoriteClick: (SearchSpellItem) -> Unit,
    onSpellClick: (SearchSpellItem) -> Unit,
) {
    CustomLazyHeaderList(
        mapOfValue = spellByLevel,
        stickyMode = false,
        header = { level ->
            Text(
                text = "Level ${level.level}",
                modifier = Modifier.clip(CutCornerShape(8.dp))
                    .background(level.color())
                    .fillMaxWidth()
                    .border(2.dp, darkBlue, CutCornerShape(8.dp))
                    .padding(8.dp),
                style = MediumBoldDarkBlue
            )
        },
        item = { spell ->
            SearchSpellItem(
                spell = spell,
                onClick = { onSpellClick(spell) },
                onFavoriteClick = { onFavoriteClick(spell) }
            )
        }
    )
}