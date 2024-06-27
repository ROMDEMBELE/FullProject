package ui.monster

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
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
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.model.monster.Challenge
import domain.model.monster.Monster
import kotlinx.coroutines.launch
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.ancient
import org.dembeyo.shared.resources.menu_monster
import org.dembeyo.shared.resources.monster
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import ui.composable.CustomLazyHeaderList
import ui.composable.DropDownTextField
import ui.composable.MediumBold
import ui.composable.SearchMenu
import ui.composable.bounceClick
import ui.composable.darkBlue
import ui.composable.darkGray
import ui.composable.darkPrimary
import ui.composable.item
import ui.composable.primary
import ui.composable.roundCornerShape
import ui.composable.secondary


class MonsterListScreen() : Screen {

    override val key: ScreenKey
        get() = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: MonsterViewModel = koinInject()
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
                            display = { "CR $rating" },
                            value = uiState.minChallenge,
                            list = Challenge.entries,
                            modifier = Modifier.weight(.5f)
                        ) {
                            viewModel.setMinChallenge(it)
                        }

                        Spacer(Modifier.height(12.dp))

                        DropDownTextField(
                            label = "Max Challenge",
                            display = { "CR $rating" },
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
                    ListOfMonster(uiState.favoriteMonsterByChallenge, viewModel)
                } else {
                    ListOfMonster(uiState.monsterByChallenge, viewModel)
                }
            }
        }
    }

    @Composable
    fun ListOfMonster(
        monsterByChallenge: Map<Challenge, List<Monster>>,
        viewModel: MonsterViewModel
    ) {
        val navigator = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()
        CustomLazyHeaderList(
            mapOfValue = monsterByChallenge,
            stickyMode = false,
            header = { challenge ->
                Text(
                    text = "CR ${challenge.rating} (${monsterByChallenge[challenge]?.size ?: 0})",
                    modifier = Modifier.clip(CutCornerShape(8.dp))
                        .background(challenge.color)
                        .border(2.dp, darkBlue, CutCornerShape(8.dp))
                        .fillMaxWidth()
                        .padding(8.dp),
                    style = MediumBold.copy(color = secondary)
                )
            },
            item = { monster ->
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
                    })
            },
        )

    }

    @Composable
    fun MonsterItem(monster: Monster, onClick: () -> Unit, onFavoriteClick: () -> Unit) {
        Button(
            shape = roundCornerShape,
            border = BorderStroke(2.dp, primary),
            contentPadding = PaddingValues(),
            modifier = Modifier.padding(4.dp).fillMaxWidth().bounceClick(),
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            onClick = onClick
        ) {
            val boxMonsterBrush =
                Brush.linearGradient(listOf(darkBlue, darkBlue, darkBlue, monster.challenge.color))
            Box(
                Modifier.background(boxMonsterBrush)
            ) {
                Image(
                    painterResource(Res.drawable.monster),
                    null,
                    colorFilter = ColorFilter.tint(primary),
                    modifier = Modifier.align(Alignment.Center).height(50.dp).alpha(.5f)
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
                        tint = if (monster.isFavorite) Color.Yellow else darkGray
                    )
                }
            }
        }
    }
}

