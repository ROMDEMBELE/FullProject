package ui.settings

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.compose.koinInject
import ui.composable.CustomButton
import ui.primary
import ui.settings.edit.EditCampaignScreen

class CampaignSettingsScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val viewModel: CampaignSettingsViewModel = koinInject()
        val uiState: CampaignSettingsUiState by viewModel.uiState.collectAsState()

        Column(Modifier.fillMaxSize().padding(horizontal = 8.dp)) {
            AnimatedContent(uiState.hasCampaignIsProgress) { displayCurrentCampaign ->
                if (displayCurrentCampaign) {
                    Text(uiState.fullName.toString())
                    Text(uiState.description.toString())
                    LinearProgressIndicator(

                        progress = uiState.progress ?: 0f,
                        color = primary,
                    )
                    CustomButton(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                        onClick = {
                            navigator.push(EditCampaignScreen())
                        }
                    ) {
                        Text("Edit Campaign")
                    }
                } else {


                    CustomButton(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                        onClick = {
                            navigator.push(EditCampaignScreen())
                        }
                    ) {
                        Text("Create a new Campaign")
                    }
                }
            }


        }
    }

}