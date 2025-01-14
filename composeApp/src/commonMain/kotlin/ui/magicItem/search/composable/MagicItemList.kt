package ui.magicItem.search

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
import domain.model.magicItem.ItemRarity
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.d20
import org.dembeyo.shared.resources.search_empty
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.composable.CustomLazyHeaderList
import ui.composable.MediumBoldSecondary
import ui.composable.darkBlue
import ui.composable.primaryDark
import ui.composable.screenTitle
import ui.getRarityColor
import ui.magicItem.search.composable.SearchMagicItemItem
import ui.stringRes

@Composable
fun MagicItemList(
    magicItemsByRarity: Map<ItemRarity, List<SearchMagicItemItem>>,
    onFavoriteClick: (SearchMagicItemItem) -> Unit,
    onItemClick: (SearchMagicItemItem) -> Unit,
) {
    if (magicItemsByRarity.isEmpty()) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(stringResource(Res.string.search_empty), style = screenTitle(primaryDark))
            Spacer(Modifier.height(36.dp))
            Image(
                painter = painterResource(Res.drawable.d20),
                colorFilter = ColorFilter.tint(primaryDark),
                contentDescription = "loading",
                modifier = Modifier.size(100.dp)
            )
        }
    } else {
        CustomLazyHeaderList(
            mapOfValue = magicItemsByRarity,
            stickyMode = false,
            header = { rarity ->
                Text(
                    text = stringResource(rarity.stringRes()),
                    modifier = Modifier.clip(CutCornerShape(8.dp))
                        .background(rarity.getRarityColor())
                        .border(2.dp, darkBlue, CutCornerShape(8.dp))
                        .fillMaxWidth()
                        .padding(8.dp),
                    style = MediumBoldSecondary
                )
            },
            item = { magicItem ->
                SearchMagicItemItem(
                    magicItem = magicItem,
                    onClick = { onItemClick(magicItem) },
                    onFavoriteClick = { onFavoriteClick(magicItem) }
                )
            },
        )
    }
}