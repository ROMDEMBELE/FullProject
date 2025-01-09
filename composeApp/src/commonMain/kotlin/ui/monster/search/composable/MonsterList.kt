package ui.monster.search.composable

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
import domain.model.monster.Challenge
import ui.color
import ui.composable.CustomLazyHeaderList
import ui.composable.MediumBoldSecondary
import ui.composable.darkBlue
import ui.monster.search.SearchMonsterItem

@Composable
fun MonsterList(
    monsterByChallenge: Map<Challenge, List<SearchMonsterItem>>,
    onFavoriteClick: (SearchMonsterItem) -> Unit,
    onMonsterClick: (SearchMonsterItem) -> Unit
) {
    CustomLazyHeaderList(
        mapOfValue = monsterByChallenge,
        stickyMode = false,
        header = { challenge ->
            Text(
                text = "CR ${challenge.rating} (${monsterByChallenge[challenge]?.size ?: 0})",
                modifier = Modifier.clip(CutCornerShape(8.dp))
                    .background(challenge.color())
                    .border(2.dp, darkBlue, CutCornerShape(8.dp))
                    .fillMaxWidth()
                    .padding(8.dp),
                style = MediumBoldSecondary,
                color = darkBlue
            )
        },
        item = { monster ->
            SearchMonsterItem(
                monster = monster,
                onClick = {
                   onMonsterClick(monster)
                },
                onFavoriteClick = {
                    onFavoriteClick(monster)
                }
            )
        },
    )
}