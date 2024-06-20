package ui.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import domain.model.Level
import domain.model.spell.Spell
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.magic
import org.jetbrains.compose.resources.painterResource
import ui.MediumBold
import ui.darkBlue
import ui.darkGray
import ui.item
import ui.primary

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListOfSpell(
    spellsByLevel: Map<Level, List<Spell>>,
    onSpellClick: (Spell) -> Unit,
    onFavoriteClick: (Spell) -> Unit
) {
    val listState =
        rememberSaveable(saver = LazyListState.Saver) {
            LazyListState(0, 0)
        }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
            .fadingEdge()
    ) {
        spellsByLevel.forEach { (level, spells) ->
            item {
                Column(Modifier.padding(vertical = 8.dp).alpha(0.9f)) {
                    Text(
                        text = "Level ${level.level}",
                        modifier = Modifier.clip(CutCornerShape(8.dp))
                            .background(level.color)
                            .fillMaxWidth()
                            .border(2.dp, darkBlue, CutCornerShape(8.dp))
                            .padding(8.dp),
                        style = MediumBold.copy(color = darkBlue)
                    )
                }
            }
            items(items = spells) {
                SpellItem(
                    modifier = Modifier.animateItemPlacement(),
                    spell = it,
                    onClick = {
                        onSpellClick(it)
                    },
                    onFavoriteClick = {
                        onFavoriteClick(it)
                    })
            }
        }
    }
}

@Composable
fun SpellItem(modifier: Modifier, spell: Spell, onClick: () -> Unit, onFavoriteClick: () -> Unit) {
    Button(
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(2.dp, primary),
        contentPadding = PaddingValues(),
        modifier = modifier.padding(4.dp).fillMaxWidth(),
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

fun Modifier.fadingEdge(
    brush: Brush = Brush.verticalGradient(
        0f to Color.Transparent,
        0.05f to Color.Red,
        0.95f to Color.Red,
        1f to Color.Transparent
    )
) = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()
        drawRect(brush = brush, blendMode = BlendMode.DstIn)
    }