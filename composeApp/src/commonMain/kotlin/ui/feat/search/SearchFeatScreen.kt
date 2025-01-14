package ui.feat.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import org.dembeyo.shared.resources.d20
import org.dembeyo.shared.resources.feat_prerequisites
import org.dembeyo.shared.resources.knight
import org.dembeyo.shared.resources.search_empty
import org.dembeyo.shared.resources.search_monster_text_field_hint
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.composable.CustomAnimatedPlaceHolder
import ui.composable.SearchMenu
import ui.composable.TaperedRule
import ui.composable.bounceClick
import ui.composable.darkBlue
import ui.composable.primaryDark
import ui.composable.item
import ui.composable.lightBlue
import ui.composable.propertyText
import ui.composable.propertyTitle
import ui.composable.roundCornerShape
import ui.composable.screenTitle

@Composable
fun SearchFeatScreen(
    viewModel: SearchFeatViewModel
) {

    val state by viewModel.state.collectAsState()

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchMenu(
            searchTextPlaceholder = stringResource(Res.string.search_monster_text_field_hint),
            searchTextFieldValue = state.searchTextFieldValue,
            onTextChange = viewModel::onSearchTextChange,
            favoriteEnabled = false,
            onFavoritesClick = null,
            filterContent = null
        )

        TaperedRule()

        if (state.isLoading) {
            CustomAnimatedPlaceHolder(
                backgroundColor = Color.Transparent,
                contentColor = primaryDark
            )
        } else {
            FeatList(featItemList = state.featList)
        }
    }
}

@Composable
fun FeatList(
    featItemList: List<FeatItem>,
) {
    val expanded = rememberSaveable { mutableStateOf<String?>(null) }
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
        LazyColumn(
            Modifier.fillMaxSize(),
        ) {
            items(featItemList) { featItem ->
                FeatItem(
                    featItem = featItem,
                    expanded = expanded.value == featItem.name,
                    onClick = {
                        if (expanded.value == featItem.name) {
                            expanded.value = null
                        } else {
                            expanded.value = featItem.name
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun FeatItem(
    featItem: FeatItem,
    expanded: Boolean,
    onClick: () -> Unit
) {
    Column {
        Button(
            shape = roundCornerShape,
            border = BorderStroke(2.dp, darkBlue),
            contentPadding = PaddingValues(),
            modifier = Modifier.padding(4.dp).fillMaxWidth().height(66.dp).bounceClick(),
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            onClick = onClick
        ) {
            val boxMonsterBrush = Brush.linearGradient(listOf(darkBlue, lightBlue))
            Box(Modifier.background(boxMonsterBrush)) {
                Image(
                    painterResource(Res.drawable.knight),
                    null,
                    colorFilter = ColorFilter.tint(darkBlue),
                    modifier = Modifier.fillMaxHeight()
                        .rotate(-20f)
                        .scale(1.5f)
                        .align(Alignment.Center)
                        .alpha(.5f)
                )
                Text(
                    featItem.name,
                    style = item,
                    modifier = Modifier.padding(8.dp)
                        .fillMaxWidth()
                        .align(Alignment.Center)
                )
            }
        }
        AnimatedVisibility(expanded) {
            Column(Modifier.fillMaxWidth().padding(8.dp)) {

                if (featItem.hasPrerequisites) {
                    TaperedRule()
                    Row(Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp)) {
                        Text(
                            stringResource(Res.string.feat_prerequisites),
                            style = propertyTitle,
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            featItem.prerequisites.toString(),
                            style = propertyText,
                        )
                    }
                }

                TaperedRule()

                featItem.benefits.forEach { description ->
                    Text(
                        description,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        style = propertyText,
                    )
                }

                TaperedRule()
            }
        }
    }
}

