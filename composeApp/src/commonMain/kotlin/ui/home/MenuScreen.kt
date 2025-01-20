package ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import ui.composable.secondary
import ui.navigation.AppRoute

@Composable
fun MenuScreen(navController: NavHostController) {

    val scope = rememberCoroutineScope()

    LazyVerticalGrid(
        modifier = Modifier.background(secondary).fillMaxSize().padding(16.dp),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        items(
            listOf(
                AppRoute.SEARCH_SPELL,
                AppRoute.SEARCH_MONSTER,
                AppRoute.SEARCH_FEAT,
                AppRoute.SEARCH_MAGIC_ITEM,
                AppRoute.SEARCH_CHARACTER,
                AppRoute.SEARCH_CAMPAIGN
                //AppRoute.SEARCH_EQUIPMENT
            )
        ) { route ->
            MenuItemView(route) {
                scope.launch {
                    delay(200)
                    navController.navigate(route.route)
                }
            }
        }
    }
}

internal class NoRippleInteractionSource : MutableInteractionSource {

    override val interactions: Flow<Interaction> = emptyFlow()

    override suspend fun emit(interaction: Interaction) {}

    override fun tryEmit(interaction: Interaction) = true
}

