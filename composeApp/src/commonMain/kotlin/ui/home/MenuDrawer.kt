package ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.composable.darkBlue
import ui.composable.secondaryDark
import ui.navigation.AppRoute

@Composable
fun MenuDrawer(
    navHostController: NavHostController,
    onDismiss: () -> Unit
) {
    ModalDrawerSheet(
        drawerContainerColor = secondaryDark
    ) {
        listOf(
            AppRoute.HOME,
            AppRoute.BATTLE,
            AppRoute.SEARCH_SPELL,
            AppRoute.SEARCH_MONSTER,
            AppRoute.SEARCH_FEAT,
            AppRoute.SEARCH_MAGIC_ITEM,
            //AppRoute.SEARCH_CHARACTER,
            //AppRoute.SEARCH_EQUIPMENT
        ).forEach { menu ->
            NavigationDrawerItem(
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = darkBlue,
                    selectedTextColor = darkBlue,
                    selectedIconColor = darkBlue,
                    unselectedContainerColor = secondaryDark,
                    unselectedTextColor = darkBlue,
                ),
                label = { Text(stringResource(menu.title)) },
                icon = {
                    Image(
                        painter = painterResource(menu.icon),
                        contentDescription = stringResource(menu.title),
                        modifier = Modifier.size(20.dp),
                        colorFilter = ColorFilter.tint(darkBlue)
                    )
                },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                selected = false,
                onClick = {
                    navHostController.navigate(menu.route);
                    onDismiss()
                }
            )
        }
    }
}