package ui.spell

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.model.Level
import domain.model.spell.Spell
import kotlinx.coroutines.launch
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.ancient
import org.dembeyo.shared.resources.magic
import org.dembeyo.shared.resources.menu_spell
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import ui.composable.CustomLazyHeaderList
import ui.composable.MediumBold
import ui.composable.SearchMenu
import ui.composable.TaperedRule
import ui.composable.bounceClick
import ui.composable.darkBlue
import ui.composable.darkGray
import ui.composable.darkPrimary
import ui.composable.item
import ui.composable.primary
import ui.composable.roundCornerShape


class SpellListScreen() : Screen {

    override val key: ScreenKey
        get() = "SpellListScreen"

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: SpellListViewModel = koinInject()
        val uiState by viewModel.uiState.collectAsState()
        var favoriteEnabled by rememberSaveable { mutableStateOf(false) }
        val scope = rememberCoroutineScope()
        Column {
            AnimatedContent(favoriteEnabled) { fav ->
                if (fav) {
                    Text(
                        "${uiState.favoritesCounter} Favorite Spells",
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 40.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(Res.font.ancient)),
                        color = darkPrimary
                    )
                } else {
                    Text(
                        stringResource(Res.string.menu_spell),
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 40.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(Res.font.ancient)),
                        color = darkPrimary
                    )
                }
            }
            TaperedRule()
            SearchMenu(
                searchTextPlaceholder = "Search by name",
                searchTextFieldValue = uiState.textField,
                onTextChange = { viewModel.filterByText(it) },
                favoriteCounter = uiState.favoritesCounter,
                filterCounter = uiState.filterCounter,
                favoriteEnabled = favoriteEnabled,
                onFavoritesClick = { favoriteEnabled = !favoriteEnabled },
            ) {
                LazyVerticalGrid(
                    modifier = Modifier.padding(8.dp),
                    columns = GridCells.Fixed(2),
                ) {
                    items(Level.entries.subList(0, 10)) { level ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.height(30.dp)
                        ) {
                            Checkbox(
                                colors = CheckboxDefaults.colors(darkBlue),
                                checked = uiState.filterByLevel.contains(level),
                                onCheckedChange = { checked ->
                                    viewModel.filterByLevel(level, checked)
                                })
                            Text(
                                text = "Level ${level.level}",
                                modifier = Modifier.weight(1f),
                                style = MediumBold.copy(
                                    color = darkBlue,
                                    textAlign = TextAlign.Start
                                )
                            )
                        }
                    }
                }
            }
            TaperedRule()
            AnimatedContent(favoriteEnabled) { fav ->
                if (fav) {
                    ListOfSpell(uiState.favoriteByLevel, viewModel)
                } else {
                    ListOfSpell(uiState.filteredSpellsByLevel, viewModel)
                }
            }
        }
    }

    @Composable
    fun ListOfSpell(spellByLevel: Map<Level, List<Spell>>, viewModel: SpellListViewModel) {
        val navigator = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()
        CustomLazyHeaderList(
            mapOfValue = spellByLevel,
            stickyMode = false,
            header = { level ->
                Text(
                    text = "Level ${level.level}",
                    modifier = Modifier.clip(CutCornerShape(8.dp))
                        .background(level.color)
                        .fillMaxWidth()
                        .border(2.dp, darkBlue, CutCornerShape(8.dp))
                        .padding(8.dp),
                    style = MediumBold.copy(color = darkBlue)
                )
            },
            item = { spell ->
                SpellItem(spell, onClick = {
                    navigator.push(SpellDetailsScreen(spell.index))
                },
                    onFavoriteClick = {
                        scope.launch {
                            viewModel.toggleSpellIsFavorite(spell)
                        }
                    })
            })
    }

    @Composable
    fun SpellItem(spell: Spell, onClick: () -> Unit, onFavoriteClick: () -> Unit) {
        Button(
            shape = roundCornerShape,
            border = BorderStroke(2.dp, primary),
            contentPadding = PaddingValues(),
            modifier = Modifier.padding(4.dp).fillMaxWidth().bounceClick(),
            colors = ButtonDefaults.buttonColors(darkBlue),
            onClick = onClick
        ) {
            val boxMonsterBrush =
                Brush.linearGradient(listOf(darkBlue, darkBlue, darkBlue, spell.level.color))
            Box(Modifier.background(boxMonsterBrush)) {
                Image(
                    painterResource(Res.drawable.magic),
                    null,
                    colorFilter = ColorFilter.tint(primary),
                    modifier = Modifier.align(Alignment.Center).height(50.dp).alpha(.5f)
                )
                Text(
                    spell.name,
                    style = item,
                    modifier = Modifier.padding(8.dp)
                        .fillMaxWidth()
                        .align(Alignment.Center)
                )
                IconButton(
                    onClick = onFavoriteClick,
                    modifier = Modifier.align(Alignment.CenterEnd).padding(10.dp)
                ) {
                    Icon(
                        Icons.Filled.Star,
                        null,
                        tint = if (spell.isFavorite) Color.Yellow else darkGray
                    )
                }
            }
        }
    }
}

