package ui.spell

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.model.Level
import kotlinx.coroutines.launch
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.ancient
import org.dembeyo.shared.resources.menu_spell
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import ui.MediumBold
import ui.composable.ListOfSpell
import ui.composable.SearchMenu
import ui.darkBlue
import ui.darkPrimary


class SpellListScreen() : Screen {

    override val key: ScreenKey
        get() = "SpellListScreen"

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: SpellViewModel = koinInject()
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
            Divider(
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                color = darkPrimary,
                thickness = 3.dp
            )
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
            Divider(
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                color = darkPrimary,
                thickness = 3.dp
            )
            AnimatedContent(favoriteEnabled) { fav ->
                if (fav) {
                    ListOfSpell(
                        spellsByLevel = uiState.favoriteByLevel,
                        onSpellClick = {
                            scope.launch {
                                viewModel.getSpellDetailsByIndex(it.index)?.let { completeSpell ->
                                    navigator.push(SpellDetailsScreen(completeSpell))
                                }
                            }
                        },
                        onFavoriteClick = {
                            scope.launch {
                                viewModel.toggleSpellIsFavorite(it)
                            }
                        }
                    )
                } else {
                    ListOfSpell(
                        spellsByLevel = uiState.filteredSpellsByLevel,
                        onSpellClick = {
                            scope.launch {
                                viewModel.getSpellDetailsByIndex(it.index)?.let { completeSpell ->
                                    navigator.push(SpellDetailsScreen(completeSpell))
                                }
                            }
                        },
                        onFavoriteClick = {
                            scope.launch {
                                viewModel.toggleSpellIsFavorite(it)
                            }
                        }
                    )
                }
            }
        }
    }
}

