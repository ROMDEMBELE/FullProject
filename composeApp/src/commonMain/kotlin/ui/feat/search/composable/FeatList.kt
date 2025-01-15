package ui.feat.search.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.d20
import org.dembeyo.shared.resources.search_empty
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.composable.primaryDark
import ui.composable.screenTitle
import ui.feat.search.SearchFeatItem

/**
 * A composable that displays a list of Feat.
 */
@Composable
fun FeatList(featItemList: List<SearchFeatItem>) {

    // The key of the expanded item null means that no item is expanded
    val expandedKey = rememberSaveable { mutableStateOf<String?>(null) }

    if (featItemList.isEmpty()) {
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
        LazyColumn(Modifier.fillMaxSize()) {
            items(featItemList) { featItem ->
                FeatItem(
                    featItem = featItem,
                    expanded = expandedKey.value == featItem.name,
                    onClick = {
                        if (expandedKey.value == featItem.name) {
                            expandedKey.value = null
                        } else {
                            expandedKey.value = featItem.name
                        }
                    }
                )
            }
        }
    }
}