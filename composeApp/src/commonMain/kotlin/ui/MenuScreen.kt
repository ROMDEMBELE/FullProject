package ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.ancient
import org.dembeyo.shared.resources.d20
import org.dembeyo.shared.resources.home
import org.dembeyo.shared.resources.knight
import org.dembeyo.shared.resources.magic
import org.dembeyo.shared.resources.magic_item
import org.dembeyo.shared.resources.menu_class
import org.dembeyo.shared.resources.menu_equipment
import org.dembeyo.shared.resources.menu_home
import org.dembeyo.shared.resources.menu_magic_item
import org.dembeyo.shared.resources.menu_monster
import org.dembeyo.shared.resources.menu_skill
import org.dembeyo.shared.resources.menu_spell
import org.dembeyo.shared.resources.monster
import org.dembeyo.shared.resources.sword_tie
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.character.CharacterCreationScreen
import ui.monster.MonsterListScreen
import ui.spell.SpellListScreen

class MenuScreen : Screen {

    override val key: ScreenKey
        get() = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Column(
            modifier = Modifier.fillMaxHeight().padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LazyVerticalGrid(
                modifier = Modifier.padding(top = 16.dp).fillMaxHeight(),
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(MenuItem.entries.filter { it != MenuItem.HOME }) { menu ->
                    MenuItemView(menu) {
                        when (menu) {
                            MenuItem.MAGIC_SPELLS -> navigator.push(SpellListScreen())
                            MenuItem.SKILL -> navigator.push(CharacterCreationScreen())
                            MenuItem.MONSTERS -> navigator.push(MonsterListScreen())
                            MenuItem.MAGIC_ITEMS -> {}
                            MenuItem.CLASS -> {}
                            MenuItem.EQUIPMENTS -> {}
                            MenuItem.HOME -> {}
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun MenuItemView(menu: MenuItem, onClick: () -> Unit) {
        Button(
            modifier = Modifier.height(240.dp),
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(4.dp, darkBlue),
            contentPadding = PaddingValues(),
            elevation = ButtonDefaults.elevation(2.dp),
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            onClick = onClick
        ) {
            Box(Modifier.fillMaxSize().background(secondary)) {
                val gradient = Brush.linearGradient(listOf(primary, darkPrimary))
                Image(
                    painterResource(menu.icon),
                    null,
                    modifier = Modifier.fillMaxWidth()
                        .padding(16.dp)
                        .alpha(0.6f)
                        .aspectRatio(1f)
                        .align(Alignment.Center)
                        .drawWithContent {
                            drawContent()
                            drawRect(gradient, blendMode = BlendMode.SrcAtop)
                        }
                )

                Text(
                    text = stringResource(menu.title),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(8.dp)
                        .fillMaxWidth(),
                    color = darkBlue,
                    textAlign = TextAlign.Center,
                    fontSize = 35.sp,
                    lineHeight = 34.sp,
                    fontFamily = FontFamily(Font(Res.font.ancient)),
                    style = TextStyle(
                        shadow = Shadow(
                            color = secondary,
                            offset = Offset(5f, 5f),
                            blurRadius = 12f
                        )
                    )
                )

            }

        }
    }

    enum class MenuItem(val title: StringResource, val icon: DrawableResource) {
        HOME(Res.string.menu_home, Res.drawable.home),
        MAGIC_SPELLS(Res.string.menu_spell, Res.drawable.magic),
        MONSTERS(Res.string.menu_monster, Res.drawable.monster),
        MAGIC_ITEMS(Res.string.menu_magic_item, Res.drawable.magic_item),
        CLASS(Res.string.menu_class, Res.drawable.knight),
        SKILL(Res.string.menu_skill, Res.drawable.d20),
        EQUIPMENTS(Res.string.menu_equipment, Res.drawable.sword_tie)
    }
}