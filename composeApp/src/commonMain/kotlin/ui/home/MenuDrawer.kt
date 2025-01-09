package ui.home

import AppRoute
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.composable.darkBlue

@Composable
fun MenuDrawer(navHostController: NavHostController, onDismiss: () -> Unit) {
    listOf(
        AppRoute.HOME,
        AppRoute.BATTLE,
        AppRoute.SEARCH_SPELL,
        AppRoute.SEARCH_MONSTER,
        AppRoute.SEARCH_MAGIC_ITEM,
        AppRoute.SEARCH_CHARACTER,
        AppRoute.SEARCH_EQUIPMENT
    ).forEach { menu ->
        TextButton(
            onClick = {
                navHostController.navigate(menu.route);
                onDismiss()
            }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(8.dp).height(40.dp),
            ) {
                Text(stringResource(menu.title), modifier = Modifier.weight(1f), color = darkBlue)
                Image(painterResource(menu.icon), null, colorFilter = ColorFilter.tint(darkBlue))
            }
        }
    }
}