package ui.magicItem.search

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
import domain.model.magicItem.Rarity
import org.jetbrains.compose.resources.stringResource
import ui.composable.CustomLazyHeaderList
import ui.composable.MediumBoldSecondary
import ui.composable.darkBlue
import ui.getRarityColor
import ui.magicItem.search.composable.SearchMagicItemItem
import ui.stringRes

@Composable
fun MagicItemList(
    magicItemsByRarity: Map<Rarity, List<SearchMagicItemItem>>,
    onFavoriteClick: (SearchMagicItemItem) -> Unit,
    onItemClick: (SearchMagicItemItem) -> Unit,
) {
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