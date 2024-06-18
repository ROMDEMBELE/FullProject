package ui.spell

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.launch
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.ancient
import org.jetbrains.compose.resources.Font
import org.koin.compose.koinInject
import ui.darkPrimary


class FavoriteSpellListScreen() : Screen {

    override val key: ScreenKey
        get() = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: SpellScreenViewModel = koinInject()
        val uiState by viewModel.uiState.collectAsState()
        val scope = rememberCoroutineScope()

        // save ui state here
        val listState =
            rememberSaveable(saver = LazyListState.Saver, key = "favorite_spell_list_state") {
                LazyListState(0, 0)
            }

        Column {
            Text(
                "Favorite Spells",
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                fontSize = 50.sp,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(Res.font.ancient)),
                color = darkPrimary
            )
            Divider(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
                color = darkPrimary,
                thickness = 3.dp
            )
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
            ) {
                items(items = uiState.favoriteSpells) {
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

