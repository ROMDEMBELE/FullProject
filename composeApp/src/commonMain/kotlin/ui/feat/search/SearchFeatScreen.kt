package ui.feat.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.search_feat_text_field_hint
import org.jetbrains.compose.resources.stringResource
import ui.composable.CustomAnimatedPlaceHolder
import ui.composable.SearchMenu
import ui.composable.TaperedRule
import ui.composable.primaryDark
import ui.feat.search.composable.FeatList

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
            searchTextPlaceholder = stringResource(Res.string.search_feat_text_field_hint),
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

