package ui.spell

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.Level
import domain.spell.Spell
import kotlinx.coroutines.launch
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.magic
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import ui.MediumBold
import ui.composable.SearchMenu
import ui.darkBlue
import ui.darkGray
import ui.item
import ui.primary


class SpellListScreen() : Screen {

    override val key: ScreenKey
        get() = uniqueScreenKey

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: SpellScreenViewModel = koinInject()
        val uiState by viewModel.uiState.collectAsState()
        val scope = rememberCoroutineScope()
        var favorites by remember { mutableStateOf(false) }

        // save ui state here
        val listState = rememberSaveable(saver = LazyListState.Saver) {
            LazyListState(0, 0)
        }

        Column {
            SearchMenu(
                searchTextPlaceholder = "Search by name",
                searchTextFieldValue = uiState.textField,
                onTextChange = { viewModel.filterByText(it) },
                favoriteCounter = uiState.favoritesCounter,
                filterCounter = uiState.filterCounter,
                onFavoritesClick = { favorites = it },
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
            // Search Bar
            AnimatedContent(favorites) { favorites ->
                if (favorites) {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
                    ) {
                        items(uiState.favorites) {
                            SpellItem(
                                spell = it,
                                onClick = {
                                    scope.launch {
                                        viewModel.getSpell(it.index)?.let { completeSpell ->
                                            navigator.push(SpellDetailsScreen(completeSpell))
                                        }
                                    }
                                },
                                onFavoriteClick = {
                                    scope.launch {
                                        viewModel.toggleSpellIsFavorite(it)
                                    }
                                })
                        }
                    }
                } else {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
                    ) {
                        uiState.spellsByLevel.forEach { (level, spells) ->
                            stickyHeader(level) {
                                Column(Modifier.padding(vertical = 8.dp).alpha(0.9f)) {
                                    Text(
                                        text = "Level ${level.level}",
                                        modifier = Modifier.clip(CutCornerShape(8.dp))
                                            .background(level.color)
                                            .fillMaxWidth()
                                            .border(2.dp, darkBlue, CutCornerShape(8.dp))
                                            .padding(8.dp),
                                        style = MediumBold.copy(color = darkBlue)
                                    )
                                }
                            }
                            items(items = spells) {
                                SpellItem(
                                    spell = it,
                                    onClick = {
                                        scope.launch {
                                            viewModel.getSpell(it.index)?.let { completeSpell ->
                                                navigator.push(SpellDetailsScreen(completeSpell))
                                            }
                                        }
                                    },
                                    onFavoriteClick = {
                                        scope.launch {
                                            viewModel.toggleSpellIsFavorite(it)
                                        }
                                    })
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun SpellItem(spell: Spell, onClick: () -> Unit, onFavoriteClick: () -> Unit) {
        Button(
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(2.dp, primary),
            contentPadding = PaddingValues(),
            modifier = Modifier.padding(4.dp).fillMaxWidth(),
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

