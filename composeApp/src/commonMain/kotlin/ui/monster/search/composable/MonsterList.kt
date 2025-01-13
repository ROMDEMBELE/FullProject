package ui.monster.search.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import domain.model.monster.Challenge
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.d20
import org.dembeyo.shared.resources.search_empty
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.color
import ui.composable.CustomLazyHeaderList
import ui.composable.MediumBoldSecondary
import ui.composable.darkBlue
import ui.composable.darkPrimary
import ui.composable.screenTitle
import ui.monster.search.SearchMonsterItem

/**
 * Composable to display a list of monsters.
 *
 * @param monsterByChallenge A map of monsters by challenge.
 * @param onFavoriteClick A callback to handle the click on a favorite button.
 * @param onMonsterClick A callback to handle the click on a monster.
 */
@Composable
fun MonsterList(
    monsterByChallenge: Map<Challenge, List<SearchMonsterItem>>,
    onFavoriteClick: (SearchMonsterItem) -> Unit,
    onMonsterClick: (SearchMonsterItem) -> Unit
) {
    if (monsterByChallenge.isEmpty()) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(stringResource(Res.string.search_empty), style = screenTitle(darkPrimary))
            Spacer(Modifier.height(36.dp))
            Image(
                painter = painterResource(Res.drawable.d20),
                colorFilter = ColorFilter.tint(darkPrimary),
                contentDescription = "loading",
                modifier = Modifier.size(100.dp)
            )
        }
    } else {
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
}