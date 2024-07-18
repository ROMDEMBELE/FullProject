package ui.campaign.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.model.Campaign
import kotlinx.coroutines.launch
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.back
import org.dembeyo.shared.resources.campaign_characters
import org.dembeyo.shared.resources.campaign_encounters
import org.dembeyo.shared.resources.castle
import org.dembeyo.shared.resources.create_campaign_button
import org.dembeyo.shared.resources.edit_button
import org.dembeyo.shared.resources.next
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import ui.campaign.edit.EditCampaignScreen
import ui.composable.CustomButton
import ui.composable.MediumBoldSecondary
import ui.composable.SmallBoldSecondary
import ui.composable.TaperedRule
import ui.composable.darkBlue
import ui.composable.lightBlue
import ui.composable.orange
import ui.composable.primary
import ui.composable.roundCornerShape
import ui.composable.screenTitle
import ui.composable.secondary

class CampaignScreen : Screen {

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()

        val viewModel: CampaignViewModel = koinInject()
        val uiState: CampaignUiState by viewModel.uiState.collectAsState()
        val infiniteTransition = rememberInfiniteTransition("infinite")
        val animatedColor by infiniteTransition.animateColor(
            initialValue = secondary,
            targetValue = lightBlue,
            animationSpec = infiniteRepeatable(
                animation = tween(10000, easing = FastOutLinearInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "castle_color"
        )
        val pagerState = rememberPagerState(0, pageCount = { uiState.listOfCampaign.size + 1 })

        Box(Modifier.background(darkBlue)) {
            Image(
                painter = painterResource(Res.drawable.castle),
                colorFilter = ColorFilter.tint(animatedColor),
                contentDescription = null,
                modifier = Modifier.size(200.dp)
                    .aspectRatio(1f)
                    .alpha(0.3f)
                    .align(Alignment.Center)
            )

            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier.fillMaxSize().align(Alignment.Center),
                pageSpacing = 8.dp
            ) { page ->
                if (page < uiState.listOfCampaign.size) {
                    val campaign = uiState.listOfCampaign[page]
                    CampaignPage(
                        campaign = campaign,
                        onEdit = {
                            navigator.push(EditCampaignScreen(campaign))
                        },
                        onStop = {
                            viewModel.stopCampaign(campaign)
                        },
                        onPlay = {
                            viewModel.playCampaign(campaign)
                        },
                    )
                } else {
                    CreateCampaignPage()
                }
            }


            Row(Modifier.align(Alignment.Center).fillMaxWidth().padding(12.dp)) {
                AnimatedVisibility(
                    pagerState.canScrollBackward,
                    modifier = Modifier.size(20.dp)
                ) {
                    IconButton(
                        enabled = pagerState.canScrollBackward,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        },
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.back),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(secondary)
                        )
                    }
                }

                Spacer(Modifier.weight(1f))

                AnimatedVisibility(
                    pagerState.canScrollForward,
                    modifier = Modifier.size(20.dp)
                ) {
                    IconButton(
                        enabled = pagerState.canScrollForward,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.next),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(secondary)
                        )
                    }
                }
            }


        }
    }

    @Composable
    fun CreateCampaignPage() {
        val navigator = LocalNavigator.currentOrThrow
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxSize()
        ) {
            CustomButton(
                onClick = {
                    navigator.push(EditCampaignScreen())
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = secondary,
                    contentColor = darkBlue
                ),
            ) {
                Text(stringResource(Res.string.create_campaign_button))
            }
        }

    }

    @Composable
    fun CampaignPage(
        campaign: Campaign,
        onEdit: () -> Unit,
        onStop: () -> Unit,
        onPlay: () -> Unit,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxSize()
        ) {
            TaperedRule(color = secondary)

            Text(
                text = campaign.name,
                style = screenTitle(secondary).copy(fontSize = 32.sp),
            )

            TaperedRule(color = secondary)

            LazyColumn(
                Modifier.fillMaxWidth()
                    .padding(8.dp)
                    .weight(.3f)
            ) {
                item {
                    Text(
                        text = campaign.description,
                        color = secondary,
                        fontSize = 16.sp,
                        fontFamily = FontFamily.Serif,
                        lineHeight = 18.sp,
                    )
                }
                if (campaign.characters.isNotEmpty()) {
                    item {
                        Text(
                            text = stringResource(Res.string.campaign_characters),
                            style = MediumBoldSecondary,
                            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                        )
                    }
                    items(campaign.characters) { character ->
                        Row(
                            Modifier.padding(8.dp)
                                .fillMaxWidth()
                                .clip(roundCornerShape)
                                .background(lightBlue)
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Text(
                                text = character.fullName,
                                style = SmallBoldSecondary,
                            )
                            Text(
                                text = character.characterClass,
                                style = SmallBoldSecondary,
                            )
                            Text(
                                text = "Level ${character.level.level}",
                                style = SmallBoldSecondary,
                            )
                            Text(
                                text = character.player,
                                style = SmallBoldSecondary,
                            )


                        }

                    }
                }
                if (campaign.encounters.isNotEmpty()) {
                    item {
                        Text(
                            text = stringResource(Res.string.campaign_encounters),
                            style = MediumBoldSecondary,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                    items(campaign.encounters) { encounter ->
                        Text(
                            text = encounter.title,
                            style = MediumBoldSecondary,
                        )
                    }
                }
            }

            TaperedRule(color = secondary)
            CustomButton(
                onClick = onEdit,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = secondary,
                    contentColor = darkBlue
                )
            ) {
                Text(text = stringResource(Res.string.edit_button))
            }
            if (campaign.inProgress) {
                CustomButton(
                    onClick = onStop,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = secondary,
                        contentColor = primary
                    )
                ) {
                    Text(text = "Stop")
                }
            } else {
                CustomButton(
                    onClick = onPlay,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = secondary,
                        contentColor = orange
                    )
                ) {
                    Text(text = "Play")
                }
            }
        }
    }
}