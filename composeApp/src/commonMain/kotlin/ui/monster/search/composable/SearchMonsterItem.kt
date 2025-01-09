package ui.monster.search.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.monster
import org.jetbrains.compose.resources.painterResource
import ui.color
import ui.composable.bounceClick
import ui.composable.darkBlue
import ui.composable.darkGray
import ui.composable.item
import ui.composable.roundCornerShape
import ui.monster.search.SearchMonsterItem


@Composable
fun searchMonsterItem(
    monster: SearchMonsterItem,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Button(
        shape = roundCornerShape,
        border = BorderStroke(2.dp, darkBlue),
        contentPadding = PaddingValues(),
        modifier = Modifier.padding(4.dp).fillMaxWidth().height(66.dp).bounceClick(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        onClick = onClick
    ) {
        val boxMonsterBrush =
            Brush.linearGradient(
                listOf(
                    darkBlue,
                    monster.challenge.color()
                )
            )
        Box(
            Modifier.background(boxMonsterBrush)
        ) {
            Image(
                painterResource(Res.drawable.monster),
                null,
                colorFilter = ColorFilter.tint(darkBlue),
                modifier = Modifier.fillMaxHeight()
                    .rotate(-20f)
                    .scale(1.5f)
                    .align(Alignment.Center)
                    .alpha(.5f)
            )
            Text(
                monster.name,
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
                    tint = if (monster.isFavorite) Color.Yellow else darkGray
                )
            }
        }
    }
}