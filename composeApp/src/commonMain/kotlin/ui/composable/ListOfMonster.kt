package ui.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import domain.monster.Challenge
import domain.monster.Monster
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.monster
import org.jetbrains.compose.resources.painterResource
import ui.MediumBold
import ui.darkBlue
import ui.darkGray
import ui.item
import ui.primary
import ui.secondary

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListOfMonster(
    monsterByChallenge: Map<Challenge, List<Monster>>,
    onMonsterClick: (Monster) -> Unit,
    onFavoriteClick: (Monster) -> Unit
) {
    val listState =
        rememberSaveable(saver = LazyListState.Saver) {
            LazyListState(0, 0)
        }
    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .fadingEdge(
                Brush.verticalGradient(
                    0f to Color.Transparent,
                    0.05f to Color.Red,
                    0.95f to Color.Red,
                    1f to Color.Transparent
                )
            )
    ) {
        monsterByChallenge.forEach { (challenge, spells) ->
            stickyHeader(challenge) {
                Column(Modifier.padding(vertical = 8.dp).alpha(0.8f)) {
                    Text(
                        text = "CR ${challenge.rating} (${monsterByChallenge[challenge]?.size ?: 0})",
                        modifier = Modifier.clip(CutCornerShape(8.dp))
                            .background(challenge.color)
                            .border(2.dp, darkBlue, CutCornerShape(8.dp))
                            .fillMaxWidth()
                            .padding(8.dp),
                        style = MediumBold.copy(color = secondary)
                    )
                }
            }
            items(items = spells) { monster ->
                MonsterItem(
                    monster = monster,
                    onClick = {
                        onMonsterClick(monster)
                    },
                    onFavoriteClick = {
                        onFavoriteClick(monster)
                    })
            }
        }
    }
}

@Composable
fun MonsterItem(monster: Monster, onClick: () -> Unit, onFavoriteClick: () -> Unit) {
    Button(
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(2.dp, primary),
        contentPadding = PaddingValues(),
        modifier = Modifier.padding(4.dp).fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        onClick = onClick
    ) {
        val boxMonsterBrush =
            Brush.linearGradient(listOf(darkBlue, darkBlue, darkBlue, monster.challenge.color))
        Box(
            Modifier.background(boxMonsterBrush)
        ) {
            Image(
                painterResource(Res.drawable.monster),
                null,
                colorFilter = ColorFilter.tint(primary),
                modifier = Modifier.align(Alignment.Center).height(50.dp).alpha(.5f)
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