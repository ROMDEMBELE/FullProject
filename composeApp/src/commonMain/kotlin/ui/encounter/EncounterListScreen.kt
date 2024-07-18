package ui.encounter

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.castle_empty
import org.dembeyo.shared.resources.create_campaign_button
import org.dembeyo.shared.resources.create_character_button
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import ui.campaign.edit.EditCampaignScreen
import ui.character.edit.EditCharacterScreen
import ui.composable.BigBold
import ui.composable.CustomAnimatedPlaceHolder
import ui.composable.CustomButton
import ui.composable.TaperedRule
import ui.composable.darkPrimary
import ui.composable.screenTitle
import ui.composable.secondary

class EncounterListScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val viewModel: EncounterListViewModel = koinInject()
        val uiState by viewModel.uiState.collectAsState()

        AnimatedContent(uiState, transitionSpec = { fadeIn().togetherWith(fadeOut()) }) { state ->
            if (state.isReady.not()) {
                CustomAnimatedPlaceHolder(backgroundColor = darkPrimary, contentColor = secondary)
            } else if (state.isReady && state.campaign == null) {
                Column(
                    Modifier.fillMaxSize().background(secondary),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        "There is no campaign in progress, please create or select a new campaign",
                        style = BigBold,
                        modifier = Modifier.padding(16.dp)
                    )

                    TaperedRule(modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp))

                    CustomButton(onClick = { navigator.push(EditCampaignScreen()) }) {
                        Text(stringResource(Res.string.create_campaign_button))
                        Spacer(Modifier.width(8.dp))
                        Image(
                            painter = painterResource(Res.drawable.castle_empty),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(secondary),
                            modifier = Modifier.size(24.dp).aspectRatio(1f)
                        )
                    }
                }
            } else if (state.isReady && state.campaign != null) {
                Column(
                    Modifier.fillMaxSize().background(secondary),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        state.campaign.name,
                        style = screenTitle(),
                        modifier = Modifier.padding(8.dp).fillMaxWidth()
                    )

                    TaperedRule()

                    if (state.encounters.isEmpty()) {
                        Spacer(Modifier.weight(1f))
                        Text("There is no encounters", style = BigBold)
                        Spacer(Modifier.weight(1f))
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            items(state.encounters) { item ->


                            }
                        }
                    }
                    TaperedRule()
                    CustomButton(
                        modifier = Modifier.fillMaxWidth()
                            .padding(bottom = 8.dp, start = 8.dp, end = 8.dp),
                        onClick = {
                            navigator.push(EditCharacterScreen())
                        }
                    ) {
                        Text(stringResource(Res.string.create_character_button))
                    }
                }
            }
        }
    }

}