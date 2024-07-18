package ui.home

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
import ui.encounter.EncounterListScreen
import ui.character.CharacterListScreen
import ui.composable.darkBlue
import ui.home.MenuScreen.MenuItem.BATTLE
import ui.home.MenuScreen.MenuItem.CHARACTERS
import ui.home.MenuScreen.MenuItem.EQUIPMENTS
import ui.home.MenuScreen.MenuItem.HOME
import ui.home.MenuScreen.MenuItem.MAGIC_ITEMS
import ui.home.MenuScreen.MenuItem.MAGIC_SPELLS
import ui.home.MenuScreen.MenuItem.MONSTERS
import ui.magicItem.list.MagicItemListScreen
import ui.monster.list.MonsterListScreen
import ui.spell.list.SpellListScreen

@Composable
fun MenuDrawer(navigator: Navigator, onDismiss: () -> Unit) {
    MenuScreen.MenuItem.entries.forEach { menu ->
        TextButton(
            onClick = {
                when (menu) {
                    HOME -> navigator.popUntil { it is MenuScreen }
                    MAGIC_SPELLS -> navigator.replaceAll(listOf(MenuScreen(), SpellListScreen()))
                    MONSTERS -> navigator.replaceAll(listOf(MenuScreen(), MonsterListScreen()))
                    MAGIC_ITEMS -> navigator.replaceAll(listOf(MenuScreen(), MagicItemListScreen()))
                    CHARACTERS -> navigator.replaceAll(listOf(MenuScreen(), CharacterListScreen()))
                    BATTLE -> navigator.replaceAll(listOf(MenuScreen(), EncounterListScreen()))
                    EQUIPMENTS -> {}
                }
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