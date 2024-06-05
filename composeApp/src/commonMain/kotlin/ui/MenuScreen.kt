package ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import fullproject.composeapp.generated.resources.Res
import fullproject.composeapp.generated.resources.d20
import fullproject.composeapp.generated.resources.home
import fullproject.composeapp.generated.resources.knight
import fullproject.composeapp.generated.resources.magic
import fullproject.composeapp.generated.resources.magic_item
import fullproject.composeapp.generated.resources.menu_class
import fullproject.composeapp.generated.resources.menu_equipment
import fullproject.composeapp.generated.resources.menu_home
import fullproject.composeapp.generated.resources.menu_magic_item
import fullproject.composeapp.generated.resources.menu_monster
import fullproject.composeapp.generated.resources.menu_skill
import fullproject.composeapp.generated.resources.menu_spell
import fullproject.composeapp.generated.resources.monster
import fullproject.composeapp.generated.resources.sword_tie
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.spell.SpellListScreen

class MenuScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            Divider(thickness = 4.dp, color = darkBlue, modifier = Modifier.padding(12.dp))

            LazyVerticalGrid(
                modifier = Modifier.padding(horizontal = 12.dp),
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(MenuItem.entries.filter { it != MenuItem.HOME }) { menu ->
                    Button(
                        modifier = Modifier.height(200.dp),
                        shape = RoundedCornerShape(20.dp),
                        border = BorderStroke(4.dp, primary),
                        colors = ButtonDefaults.buttonColors(darkBlue),
                        onClick = {
                            when (menu) {
                                MenuItem.MAGIC_SPELLS -> navigator.push(SpellListScreen())
                                else -> {}
                                /*
                                MenuItem.MONSTERS -> navigator.push(menu.name)
                                MenuItem.MAGIC_ITEMS -> navigator.push(menu.name)
                                MenuItem.CLASS -> navigator.push(menu.name)
                                MenuItem.SKILL -> navigator.push(menu.name)
                                MenuItem.EQUIPMENTS -> navigator.push(menu.name)

                                 */
                            }
                        }
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Image(
                                painterResource(menu.icon),
                                null,
                                colorFilter = ColorFilter.tint(primary),
                                modifier = Modifier.fillMaxSize().weight(1f).aspectRatio(1f)
                            )
                            Text(
                                text = stringResource(menu.title),
                                modifier = Modifier.clip(CutCornerShape(8.dp))
                                    .background(lightBlue).fillMaxWidth().padding(4.dp),
                                style = mediumBoldWhite.copy(color = darkBlue)
                            )
                        }

                    }
                }
            }

            Divider(thickness = 4.dp, color = darkBlue, modifier = Modifier.padding(12.dp))

        }
    }

    enum class MenuItem(val route: String, val title: StringResource, val icon: DrawableResource) {
        HOME("home", Res.string.menu_home, Res.drawable.home),
        MAGIC_SPELLS("spells", Res.string.menu_spell, Res.drawable.magic),
        MONSTERS("monsters", Res.string.menu_monster, Res.drawable.monster),
        MAGIC_ITEMS("items", Res.string.menu_magic_item, Res.drawable.magic_item),
        CLASS("class", Res.string.menu_class, Res.drawable.knight),
        SKILL("skills", Res.string.menu_skill, Res.drawable.d20),
        EQUIPMENTS("equipments", Res.string.menu_equipment, Res.drawable.sword_tie)
    }
}