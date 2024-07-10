package ui.monster.list

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.RangeSlider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.model.monster.Challenge
import domain.model.monster.Monster
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.error_dialog_title
import org.dembeyo.shared.resources.monster
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import ui.composable.BigBold
import ui.composable.CustomAnimatedPlaceHolder
import ui.composable.CustomButton
import ui.composable.CustomErrorDialog
import ui.composable.CustomLazyHeaderList
import ui.composable.MediumBold
import ui.composable.SearchMenu
import ui.composable.SmallBold
import ui.composable.TaperedRule
import ui.composable.bounceClick
import ui.composable.darkBlue
import ui.composable.darkGray
import ui.composable.darkPrimary
import ui.composable.item
import ui.composable.primary
import ui.composable.propertyText
import ui.composable.roundCornerShape
import ui.composable.secondary
import ui.monster.details.MonsterDetailScreen


class MonsterListScreen() : Screen {

    override val key: ScreenKey
        get() = uniqueScreenKey

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        val viewModel: MonsterListViewModel = koinInject()
        val uiState by viewModel.uiState.collectAsState()
        var favoriteEnabled by rememberSaveable { mutableStateOf(false) }
        AnimatedVisibility(uiState.hasError) {
            CustomErrorDialog(
                stringResource(Res.string.error_dialog_title),
                uiState.error.orEmpty()
            ) {
                viewModel.acknowledgeError()
            }
        }

        CustomAnimatedPlaceHolder()

        AnimatedVisibility(uiState.isReady, enter = fadeIn()) {
            Column(
                Modifier.fillMaxSize().background(secondary),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (uiState.monsterList.isEmpty()) {
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
                        favoriteCounter = uiState.favoriteCounter,
                        favoriteEnabled = favoriteEnabled,
                        onFavoritesClick = { favoriteEnabled = !favoriteEnabled },
                        filterContent = {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        "Min CR ${uiState.minChallenge.rating}",
                                        style = propertyText,
                                        color = darkPrimary
                                    )

                                    Text(
                                        "Filter by Challenge",
                                        style = SmallBold,
                                        color = darkPrimary,
                                    )

                                    Text(
                                        "Max CR ${uiState.maxChallenge.rating}",
                                        style = propertyText,
                                        color = darkPrimary
                                    )

                                }

                                RangeSlider(
                                    value = uiState.filterChallengeRange,
                                    onValueChange = { viewModel.setChallengeRange(it) },
                                    valueRange = uiState.challengeRange,
                                    steps = Challenge.entries.size,
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = SliderDefaults.colors(
                                        thumbColor = darkPrimary,
                                        activeTrackColor = darkPrimary,
                                        inactiveTrackColor = darkGray
                                    )
                                )

                            }
                        },
                    )

                    TaperedRule()

                    AnimatedContent(favoriteEnabled) { favorite ->
                        if (favorite) {
                            ListOfMonster(uiState.favoriteMonsterByChallenge, viewModel)
                        } else {
                            ListOfMonster(uiState.monsterByChallenge, viewModel)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun ListOfMonster(
        monsterByChallenge: Map<Challenge, List<Monster>>,
        viewModel: MonsterListViewModel
    ) {
        val navigator = LocalNavigator.currentOrThrow
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
                        navigator.push(MonsterDetailScreen(monster.index))
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
            modifier = Modifier.padding(4.dp).fillMaxWidth().height(66.dp).bounceClick(),
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            onClick = onClick
        ) {
            val boxMonsterBrush =
                Brush.linearGradient(
                    listOf(
                        darkBlue,
                        monster.challenge.color
                    )
                )
            Box(
                Modifier.background(boxMonsterBrush)
            ) {
                Image(
                    painterResource(Res.drawable.monster),
                    null,
                    colorFilter = ColorFilter.tint(primary),
                    modifier = Modifier.fillMaxHeight()
                        .rotate(-20f)
                        .scale(1.5f)
                        .align(Alignment.Center)
                        .alpha(.5f)
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

