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
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.MenuScreen
import ui.MenuScreen.MenuItem.CLASS
import ui.MenuScreen.MenuItem.EQUIPMENTS
import ui.MenuScreen.MenuItem.HOME
import ui.MenuScreen.MenuItem.MAGIC_ITEMS
import ui.MenuScreen.MenuItem.MAGIC_SPELLS
import ui.MenuScreen.MenuItem.MONSTERS
import ui.MenuScreen.MenuItem.SKILL
import ui.character.CharacterCreationScreen
import ui.lightGray
import ui.monster.MonsterListScreen
import ui.spell.SpellListScreen

@Composable
fun MenuDrawer(navigator: Navigator, onDismiss: () -> Unit) {
    MenuScreen.MenuItem.entries.forEach { menu ->
        TextButton(
            onClick = {
                when (menu) {
                    HOME -> navigator.popUntil { it is MenuScreen }
                    MAGIC_SPELLS -> navigator.replaceAll(listOf(MenuScreen(), SpellListScreen()))
                    MONSTERS -> navigator.replaceAll(listOf(MenuScreen(), MonsterListScreen()))
                    MAGIC_ITEMS -> {}
                    CLASS -> {}
                    SKILL -> navigator.replaceAll(listOf(MenuScreen(), CharacterCreationScreen()))
                    EQUIPMENTS -> {}
                }
                onDismiss()
            }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(8.dp).height(40.dp),
            ) {
                Text(stringResource(menu.title), modifier = Modifier.weight(1f), color = lightGray)
                Image(painterResource(menu.icon), null, colorFilter = ColorFilter.tint(lightGray))
            }
        }
    }
}