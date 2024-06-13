package ui.monster

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.Challenge
import domain.Monster
import kotlinx.coroutines.launch
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.monster
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import ui.composable.DropDownTextField
import ui.composable.SearchMenu
import ui.darkBlue
import ui.item
import ui.lightBlue
import ui.mediumBoldWhite
import ui.primary


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
        var favorite by rememberSaveable { mutableStateOf(false) }

        // save ui state here
        val listState = rememberSaveable(saver = LazyListState.Saver) {
            LazyListState(0, 0)
        }

        Column {
            SearchMenu(
                searchTextPlaceholder = "Search by name",
                searchTextFieldValue = uiState.textField,
                onTextChange = { viewModel.filterByText(it) },
                favoriteCounter = uiState.favoriteCounter,
                onFavoritesClick = { enabled -> favorite = enabled },
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

            AnimatedContent(favorite) { favorite ->
                if (favorite) {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
                    ) {
                        items(items = uiState.favorites) { monster ->
                            MonsterItem(
                                monster = monster,
                                onClick = {
                                    scope.launch {
                                        viewModel.getMonster(monster.index)?.let {
                                            navigator.push(MonsterDetailScreen(it))
                                        }
                                    }
                                },
                                onFavoriteClick = {
                                    viewModel.toggleMonsterFavorite(monster)
                                }
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
                    ) {
                        uiState.monsterByChallenge.forEach { (challenge, spells) ->
                            stickyHeader(challenge) {
                                Column(Modifier.padding(vertical = 8.dp).alpha(0.7f)) {
                                    Text(
                                        text = "CR ${challenge.rating}",
                                        modifier = Modifier.clip(CutCornerShape(8.dp))
                                            .background(challenge.color)
                                            .fillMaxWidth()
                                            .padding(8.dp),
                                        style = mediumBoldWhite.copy(color = darkBlue)
                                    )
                                }
                            }
                            items(items = spells) { monster ->
                                MonsterItem(
                                    monster = monster,
                                    onClick = {
                                        scope.launch {
                                            viewModel.getMonster(monster.index)
                                                ?.let { completeMonster ->
                                                    navigator.push(
                                                        MonsterDetailScreen(
                                                            completeMonster
                                                        )
                                                    )
                                                }
                                        }
                                    },
                                    onFavoriteClick = {
                                        scope.launch {
                                            viewModel.toggleMonsterFavorite(monster)
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
    fun MonsterItem(monster: Monster, onClick: () -> Unit, onFavoriteClick: () -> Unit) {
        Button(
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(2.dp, primary),
            modifier = Modifier.padding(4.dp).fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(darkBlue),
            onClick = onClick
        ) {
            Box {
                Image(
                    painterResource(Res.drawable.monster),
                    null,
                    colorFilter = ColorFilter.tint(primary),
                    modifier = Modifier.align(Alignment.Center).height(50.dp).alpha(.7f)
                )
                Text(
                    monster.name,
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
                        tint = if (monster.isFavorite) Color.Yellow else lightBlue
                    )
                }
            }
        }
    }
}

