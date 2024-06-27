package ui.campaign.main

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.model.Campaign
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.castle
import org.dembeyo.shared.resources.create_campaign_button
import org.dembeyo.shared.resources.edit_button
import org.dembeyo.shared.resources.no_campaign
import org.dembeyo.shared.resources.select_campaign
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import ui.campaign.edit.EditCampaignScreen
import ui.composable.BigBold
import ui.composable.CustomButton
import ui.composable.MediumBold
import ui.composable.darkBlue
import ui.composable.darkPrimary
import ui.composable.fadingEdge
import ui.composable.lightBlue
import ui.composable.roundCornerShape
import ui.composable.screenTitle
import ui.composable.secondary

class CampaignMainScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val viewModel: CampaignMainViewModel = koinInject()
        val uiState: CampaignMainUiState by viewModel.uiState.collectAsState()
        val infiniteTransition = rememberInfiniteTransition("infinite")
        val color by infiniteTransition.animateColor(
            initialValue = secondary,
            targetValue = lightBlue,
            animationSpec = infiniteRepeatable(
                animation = tween(10000, easing = FastOutLinearInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "color castle"
        )

        Surface(color = darkBlue) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(Res.drawable.castle),
                    colorFilter = ColorFilter.tint(color),
                    contentDescription = null,
                    modifier = Modifier.size(128.dp).aspectRatio(1f).padding(16.dp)
                )

                Divider(
                    color = secondary,
                    thickness = 2.dp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                AnimatedVisibility(uiState.isReady) {
                    AnimatedContent(uiState.campaignInProgress) { mainCampaign ->
                        if (mainCampaign == null) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = stringResource(Res.string.no_campaign),
                                    style = MediumBold.copy(fontSize = 30.sp),
                                    modifier = Modifier.padding(26.dp)
                                )

                                Text(
                                    text = stringResource(Res.string.select_campaign),
                                    style = BigBold.copy(color = secondary),
                                )

                                Spacer(Modifier.height(16.dp))

                                LazyColumn(Modifier.width(300.dp).height(400.dp)) {
                                    items(uiState.listOfCampaign) { campaign ->
                                        CampaignItem(campaign) {
                                            viewModel.selectCampaignAsMain(
                                                campaign
                                            )
                                        }
                                    }
                                    item {
                                        CustomButton(
                                            modifier = Modifier.fillMaxWidth(),
                                            onClick = {
                                                navigator.push(EditCampaignScreen())
                                            }
                                        ) {
                                            Text(stringResource(Res.string.create_campaign_button))
                                        }
                                    }
                                }
                            }
                        } else {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = mainCampaign.name,
                                    style = screenTitle(secondary).copy(fontSize = 32.sp),
                                    modifier = Modifier.fillMaxWidth().padding(26.dp)
                                )

                                Divider(
                                    color = secondary,
                                    thickness = 2.dp,
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )

                                LazyColumn(
                                    Modifier.fillMaxWidth()
                                        .fadingEdge()
                                        .weight(1f)
                                        .padding(vertical = 8.dp, horizontal = 24.dp)
                                ) {
                                    item {
                                        Text(
                                            text = mainCampaign.description,
                                            color = secondary,
                                            fontSize = 16.sp,
                                            fontFamily = FontFamily.Serif,
                                            lineHeight = 18.sp,
                                        )
                                    }
                                }

                                Divider(
                                    color = secondary,
                                    thickness = 2.dp,
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )

                                Spacer(Modifier.height(8.dp))

                                CustomButton(
                                    modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = secondary,
                                        contentColor = darkBlue
                                    ),
                                    onClick = { navigator.push(EditCampaignScreen(mainCampaign)) }
                                ) {
                                    Text(stringResource(Res.string.edit_button))
                                }

                                CustomButton(
                                    onClick = { viewModel.closeMainCampaign() },
                                    modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
                                ) {
                                    Text(stringResource(Res.string.no_campaign))
                                }

                                Spacer(Modifier.height(8.dp))

                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun CampaignItem(campaign: Campaign, onClick: () -> Unit) {
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed by interactionSource.collectIsPressedAsState()
        val animatedColor by animateColorAsState(if (!isPressed) secondary else darkPrimary)
        val animatedTextColor by animateColorAsState(if (isPressed) secondary else darkPrimary)
        Button(
            colors = ButtonDefaults.buttonColors(animatedColor),
            shape = roundCornerShape,
            onClick = onClick,
            interactionSource = interactionSource,
            modifier = Modifier.height(64.dp).padding(vertical = 4.dp)
        ) {
            Text(
                text = campaign.name.capitalize(Locale.current),
                color = animatedTextColor,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Serif,
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}