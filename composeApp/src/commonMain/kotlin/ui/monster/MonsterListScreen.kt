package ui.monster

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import domain.monster.Challenge
import kotlinx.coroutines.launch
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.ancient
import org.dembeyo.shared.resources.menu_monster
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import ui.composable.DropDownTextField
import ui.composable.ListOfMonster
import ui.composable.SearchMenu
import ui.darkPrimary


class MonsterListScreen() : Screen {

    override val key: ScreenKey
        get() = uniqueScreenKey

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: MonsterScreenViewModel = koinInject()
        val uiState by viewModel.uiState.collectAsState()
        val scope = rememberCoroutineScope()
        var favoriteEnabled by rememberSaveable { mutableStateOf(false) }
        Column {
            AnimatedContent(favoriteEnabled) { fav ->
                if (fav) {
                    Text(
                        "${uiState.favoriteCounter} Favorite Monsters",
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 40.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(Res.font.ancient)),
                        color = darkPrimary
                    )
                } else {
                    Text(
                        stringResource(Res.string.menu_monster),
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
                favoriteCounter = uiState.favoriteCounter,
                favoriteEnabled = favoriteEnabled,
                onFavoritesClick = { favoriteEnabled = !favoriteEnabled },
                filterContent = {
                    Column(
                        modifier = Modifier.padding(12.dp),
                    ) {
                        DropDownTextField(
                            label = "Min Challenge",
                            value = uiState.minChallenge,
                            list = Challenge.entries,
                            modifier = Modifier.weight(.5f)
                        ) {
                            viewModel.setMinChallenge(it)
                        }

                        Spacer(Modifier.height(12.dp))

                        DropDownTextField(
                            label = "Max Challenge",
                            value = uiState.maxChallenge,
                            list = Challenge.entries,
                            modifier = Modifier.weight(.5f)
                        ) {
                            viewModel.setMaxChallenge(it)
                        }
                    }
                },
            )
            Divider(
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                color = darkPrimary,
                thickness = 3.dp
            )
            AnimatedContent(favoriteEnabled) { favorite ->
                if (favorite) {
                    ListOfMonster(
                        monsterByChallenge = uiState.favoriteMonsterByChallenge,
                        onMonsterClick = {
                            scope.launch {
                                viewModel.getMonster(it.index)?.let {
                                    navigator.push(MonsterDetailScreen(it))
                                }
                            }
                        },
                        onFavoriteClick = {
                            viewModel.toggleMonsterFavorite(it)
                        })
                } else {
                    ListOfMonster(
                        monsterByChallenge = uiState.monsterByChallenge,
                        onMonsterClick = {
                            scope.launch {
                                viewModel.getMonster(it.index)?.let {
                                    navigator.push(MonsterDetailScreen(it))
                                }
                            }
                        },
                        onFavoriteClick = {
                            viewModel.toggleMonsterFavorite(it)
                        })
                }
            }
        }
    }
}

