package ui.spell

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.Level
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import ui.MediumBold
import ui.composable.SearchMenu
import ui.darkBlue


class SpellListScreen : Screen {

    override val key: ScreenKey
        get() = uniqueScreenKey

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: SpellScreenViewModel = koinInject()
        val uiState by viewModel.uiState.collectAsState()
        val scope = rememberCoroutineScope()

        // save ui state here
        val listState = rememberSaveable(saver = LazyListState.Saver, key = "spell_list_state") {
            LazyListState(0, 0)
        }

        Column {
            SearchMenu(
                searchTextPlaceholder = "Search by name",
                searchTextFieldValue = uiState.textField,
                onTextChange = { viewModel.filterByText(it) },
                favoriteCounter = uiState.favoritesCounter,
                filterCounter = uiState.filterCounter,
                onFavoritesClick = { navigator.push(FavoriteSpellListScreen()) },
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

