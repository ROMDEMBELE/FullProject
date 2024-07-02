package ui.spell

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.model.Level
import domain.model.spell.Spell
import kotlinx.coroutines.launch
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.ornament
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import ui.composable.BigBold
import ui.composable.CustomAnimatedPlaceHolder
import ui.composable.CustomButton
import ui.composable.CustomErrorDialog
import ui.composable.CustomLazyHeaderList
import ui.composable.MediumBold
import ui.composable.SearchMenu
import ui.composable.TaperedRule
import ui.composable.bounceClick
import ui.composable.darkBlue
import ui.composable.darkGray
import ui.composable.item
import ui.composable.primary
import ui.composable.roundCornerShape
import ui.composable.yellow


class SpellListScreen() : Screen {

    override val key: ScreenKey
        get() = uniqueScreenKey

    @Composable
    override fun Content() {
        val infiniteTransition = rememberInfiniteTransition()
        val viewModel: SpellListViewModel = koinInject()
        val uiState by viewModel.uiState.collectAsState()
        var showFavorite by rememberSaveable { mutableStateOf(false) }

        AnimatedVisibility(!uiState.error.isNullOrEmpty()) {
            CustomErrorDialog("Oups, Something went Wrong", uiState.error.orEmpty()) {
                viewModel.acknowledgeError()
            }
        }
        AnimatedContent(uiState.spellByLevel) { list ->
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (list.isEmpty()) {
                    Text("The spells database is empty...", style = BigBold)
                    Spacer(Modifier.height(8.dp))
                    CustomButton(onClick = { viewModel.refresh() }) {
                        Text("Refresh", style = MediumBold)
                    }
                } else {
                    SearchMenu(
                        searchTextPlaceholder = "Search by name",
                        searchTextFieldValue = uiState.textField,
                        onTextChange = { viewModel.filterByText(it) },
                        favoriteCounter = uiState.favoritesCounter,
                        filterCounter = uiState.filterCounter,
                        favoriteEnabled = showFavorite,
                        onFavoritesClick = { showFavorite = !showFavorite },
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
                    AnimatedContent(showFavorite) { fav ->
                        if (fav) {
                            ListOfSpell(uiState.favoriteByLevel, viewModel)
                        } else {
                            ListOfSpell(uiState.filteredSpellsByLevel, viewModel)
                        }
                    }
                }
            }
        }

        CustomAnimatedPlaceHolder(!uiState.isReady, infiniteTransition)
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
            modifier = Modifier.padding(4.dp).fillMaxWidth().height(66.dp).bounceClick(),
            colors = ButtonDefaults.buttonColors(darkBlue),
            onClick = onClick
        ) {
            val brush = Brush.linearGradient(listOf(darkBlue, darkBlue, spell.level.color))
            Box(Modifier.background(brush)) {
                Image(
                    painterResource(Res.drawable.ornament),
                    null,
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(primary),
                    modifier = Modifier.fillMaxWidth().scale(1.2f).align(Alignment.Center)
                        .alpha(.3f)
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
                        Icons.Default.Star,
                        null,
                        tint = if (spell.isFavorite) yellow else darkGray
                    )
                }
            }
        }
    }
}


