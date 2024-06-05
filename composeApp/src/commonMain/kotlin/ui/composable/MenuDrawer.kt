package ui.composable

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
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.MenuScreen
import ui.darkBlue

@Composable
fun MenuDrawer(onMenuItemClick: (menu: MenuScreen.MenuItem) -> Unit) {
    MenuScreen.MenuItem.entries.forEach { menu ->
        TextButton(
            onClick = { onMenuItemClick(menu) }
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