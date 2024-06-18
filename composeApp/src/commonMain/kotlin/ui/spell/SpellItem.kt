package ui.spell

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import domain.spell.Spell
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.magic
import org.jetbrains.compose.resources.painterResource
import ui.darkBlue
import ui.darkGray
import ui.item
import ui.primary

@Composable
fun SpellItem(spell: Spell, onClick: () -> Unit, onFavoriteClick: () -> Unit) {
    Button(
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(2.dp, primary),
        contentPadding = PaddingValues(),
        modifier = Modifier.padding(4.dp).fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(darkBlue),
        onClick = onClick
    ) {
        val boxMonsterBrush =
            Brush.linearGradient(listOf(darkBlue, darkBlue, darkBlue, spell.level.color))
        Box(Modifier.background(boxMonsterBrush)) {
            Image(
                painterResource(Res.drawable.magic),
                null,
                colorFilter = ColorFilter.tint(primary),
                modifier = Modifier.align(Alignment.Center).height(50.dp).alpha(.5f)
            )
            Text(
                spell.name,
                style = item,
                modifier = Modifier.padding(8.dp)
                    .fillMaxWidth()
                    .align(Alignment.Center)
            )
            IconButton(
                onClick = onFavoriteClick,
                modifier = Modifier.align(Alignment.CenterEnd).padding(10.dp)
            ) {
                Icon(
                    Icons.Filled.Star,
                    null,
                    tint = if (spell.isFavorite) Color.Yellow else darkGray
                )
            }
        }
    }
}