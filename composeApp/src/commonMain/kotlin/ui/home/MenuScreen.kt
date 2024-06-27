package ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.ancient
import org.dembeyo.shared.resources.battle
import org.dembeyo.shared.resources.home
import org.dembeyo.shared.resources.knight
import org.dembeyo.shared.resources.magic
import org.dembeyo.shared.resources.magic_item
import org.dembeyo.shared.resources.menu_battle
import org.dembeyo.shared.resources.menu_character
import org.dembeyo.shared.resources.menu_equipment
import org.dembeyo.shared.resources.menu_home
import org.dembeyo.shared.resources.menu_magic_item
import org.dembeyo.shared.resources.menu_monster
import org.dembeyo.shared.resources.menu_spell
import org.dembeyo.shared.resources.monster
import org.dembeyo.shared.resources.sword_tie
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.battle.BattleScreen
import ui.character.CharacterListScreen
import ui.composable.bounceClick
import ui.composable.darkBlue
import ui.composable.darkGray
import ui.composable.darkPrimary
import ui.composable.primary
import ui.composable.roundCornerShape
import ui.composable.secondary
import ui.monster.MonsterListScreen
import ui.spell.SpellListScreen

class MenuScreen : Screen {

    override val key: ScreenKey
        get() = uniqueScreenKey

    @Composable
    override fun Content() {
        val scope = rememberCoroutineScope()
        val navigator = LocalNavigator.currentOrThrow
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(MenuItem.entries.filter { it != MenuItem.HOME }) { menu ->
                MenuItemView(menu) {
                    scope.launch {
                        delay(200)
                        when (menu) {
                            MenuItem.MAGIC_SPELLS -> navigator.push(SpellListScreen())
                            MenuItem.BATTLE -> navigator.push(BattleScreen())
                            MenuItem.MONSTERS -> navigator.push(MonsterListScreen())
                            MenuItem.MAGIC_ITEMS -> {}
                            MenuItem.CHARACTERS -> navigator.push(CharacterListScreen())
                            MenuItem.EQUIPMENTS -> {}
                            MenuItem.HOME -> {}
                        }
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

    @Composable
    fun MenuItemView(menu: MenuItem, onClick: () -> Unit) {
        val interactionSource = remember { NoRippleInteractionSource() }
        Button(
            modifier = Modifier.height(240.dp).bounceClick(),
            shape = roundCornerShape,
            interactionSource = interactionSource,
            border = BorderStroke(2.dp, secondary),
            elevation = ButtonDefaults.elevation(4.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(darkBlue),
            onClick = onClick,
        ) {
            val surfaceColorGradient =
                Brush.linearGradient(listOf(darkBlue, darkBlue, darkGray, darkBlue, darkBlue))
            Surface(
                color = Color.Transparent,
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(2.dp, secondary),
                modifier = Modifier.padding(8.dp).clip(RoundedCornerShape(10.dp))
                    .background(surfaceColorGradient)
            ) {
                Box(Modifier.fillMaxSize().padding(8.dp)) {
                    val iconColorGradient = Brush.linearGradient(listOf(primary, darkPrimary))

                    Image(
                        painter = painterResource(menu.icon),
                        contentDescription = menu.icon.toString(),
                        modifier = Modifier.size(130.dp)
                            .aspectRatio(1f)
                            .alpha(0.6f)
                            .drawWithContent {
                                drawContent()
                                drawRect(iconColorGradient, blendMode = BlendMode.SrcAtop)
                            }
                            .align(Alignment.Center)
                    )

                    Text(
                        text = stringResource(menu.title),
                        modifier = Modifier.align(Alignment.Center).fillMaxWidth(),
                        color = secondary,
                        textAlign = TextAlign.Center,
                        fontSize = 35.sp,
                        fontFamily = FontFamily(Font(Res.font.ancient)),
                        style = TextStyle(
                            shadow = Shadow(
                                color = primary,
                                offset = Offset(5f, 5f),
                                blurRadius = 12f
                            )
                        )
                    )

                }
            }
        }
    }

    enum class MenuItem(val title: StringResource, val icon: DrawableResource) {
        HOME(Res.string.menu_home, Res.drawable.home),
        MAGIC_SPELLS(Res.string.menu_spell, Res.drawable.magic),
        MONSTERS(Res.string.menu_monster, Res.drawable.monster),
        BATTLE(Res.string.menu_battle, Res.drawable.battle),
        MAGIC_ITEMS(Res.string.menu_magic_item, Res.drawable.magic_item),
        CHARACTERS(Res.string.menu_character, Res.drawable.knight),
        EQUIPMENTS(Res.string.menu_equipment, Res.drawable.sword_tie)
    }
}