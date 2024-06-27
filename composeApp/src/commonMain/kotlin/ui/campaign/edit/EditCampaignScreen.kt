package ui.campaign.edit

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.model.Campaign
import kotlinx.coroutines.launch
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.delete_button
import org.dembeyo.shared.resources.delete_campaign
import org.dembeyo.shared.resources.delete_campaign_confirm
import org.dembeyo.shared.resources.edit_campaign_description
import org.dembeyo.shared.resources.edit_campaign_screen
import org.dembeyo.shared.resources.edit_campaign_title
import org.dembeyo.shared.resources.save_button
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import ui.composable.CustomAlertDialog
import ui.composable.CustomButton
import ui.composable.darkBlue
import ui.composable.darkPrimary
import ui.composable.primary
import ui.composable.screenTitle
import ui.composable.secondary

class EditCampaignScreen(val campaign: Campaign? = null) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()
        val viewModel: EditCampaignViewModel = koinInject()
        val uiState by viewModel.uiState.collectAsState()
        var deleteDialogDisplay by remember { mutableStateOf(false) }

        LaunchedEffect(campaign) {
            if (campaign != null) {
                viewModel.setEdit(campaign)
            }
        }


        AnimatedVisibility(deleteDialogDisplay) {
            CustomAlertDialog(
                title = stringResource(Res.string.delete_campaign),
                content = stringResource(
                    Res.string.delete_campaign_confirm,
                ),
                onConfirm = {
                    deleteDialogDisplay = false
                    scope.launch {
                        if (viewModel.deleteCampaign(false)) {
                            navigator.pop()
                        }
                    }

                },
                onDismiss = {
                    deleteDialogDisplay = false
                }
            )
        }


        Column(
            modifier = Modifier.fillMaxSize().background(darkBlue).padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(Res.string.edit_campaign_screen),
                modifier = Modifier.fillMaxWidth(),
                style = screenTitle(secondary)
            )

            Spacer(Modifier.height(12.dp))

            TextField(
                value = uiState.name,
                onValueChange = { viewModel.updateName(it) },
                placeholder = { Text(stringResource(Res.string.edit_campaign_title)) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = secondary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    textColor = darkBlue
                )
            )

            Spacer(Modifier.height(8.dp))

            TextField(
                value = uiState.description,
                onValueChange = { viewModel.updateDescription(it) },
                placeholder = { Text(stringResource(Res.string.edit_campaign_description)) },
                modifier = Modifier.fillMaxWidth().weight(1f),
                singleLine = false,
                maxLines = 30,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = secondary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    textColor = darkBlue
                )
            )

            Spacer(Modifier.height(8.dp))

            CustomButton(
                enabled = uiState.isValid,
                onClick = {
                    scope.launch {
                        viewModel.saveCampaign()
                        navigator.pop()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(Res.string.save_button))
            }
            CustomButton(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = primary,
                    contentColor = darkPrimary
                ),
                onClick = { deleteDialogDisplay = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(Res.string.delete_button))
            }

        }
    }

}