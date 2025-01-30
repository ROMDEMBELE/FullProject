package ui.campaign.save

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.delete_button
import org.dembeyo.shared.resources.delete_campaign
import org.dembeyo.shared.resources.delete_campaign_confirm
import org.dembeyo.shared.resources.edit_campaign_description
import org.dembeyo.shared.resources.edit_campaign_title
import org.dembeyo.shared.resources.save_button
import org.jetbrains.compose.resources.stringResource
import ui.composable.CustomAlertDialog
import ui.composable.CustomButton
import ui.composable.darkBlue
import ui.composable.primary
import ui.composable.primaryDark
import ui.composable.roundCornerShape
import ui.composable.screenTitle
import ui.composable.secondary

@Composable
fun SaveCampaignScreen(
    navHostController: NavHostController,
    viewModel: SaveCampaignViewModel,
    index: String? = null
) {
    val scope = rememberCoroutineScope()

    val uiState by viewModel.state.collectAsState()
    var deleteDialogDisplay by remember { mutableStateOf(false) }

    LaunchedEffect(index) {
        if (index != null) {
            viewModel.fetchCampaign(index)
        }
    }

    AnimatedVisibility(deleteDialogDisplay)
    {
        CustomAlertDialog(
            title = stringResource(Res.string.delete_campaign),
            content = stringResource(
                Res.string.delete_campaign_confirm,
            ),
            onConfirm = {
                deleteDialogDisplay = false
                scope.launch {
                    if (index != null)
                        viewModel.deleteCampaign(index) {
                            navHostController.popBackStack()
                        }
                }
            },
            onDismiss = {
                deleteDialogDisplay = false
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize().background(darkBlue).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(stringResource(Res.string.edit_campaign_title), style = screenTitle(secondary))

        TextField(
            value = uiState.name,
            shape = roundCornerShape,
            singleLine = true,
            onValueChange = viewModel::onNameChange,
            placeholder = { Text("ex : Le Murmure de la Forêt") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = darkBlue,
                unfocusedTextColor = darkBlue,
                focusedContainerColor = secondary,
                unfocusedContainerColor = secondary,
            )
        )

        Text(stringResource(Res.string.edit_campaign_description), style = screenTitle(secondary))

        TextField(
            value = uiState.description,
            shape = roundCornerShape,
            onValueChange = viewModel::onDescriptionChange,
            placeholder = { Text("Une ancienne forêt s’est éveillée avec des esprits malveillants et des murmures étranges. Les aventuriers doivent découvrir l’histoire sombre qui a réveillé ces esprits et affronter le cœur maléfique de la forêt pour restaurer la paix dans la région.") },
            modifier = Modifier.height(400.dp).fillMaxWidth(),
            singleLine = false,
            maxLines = 30,
            colors = TextFieldDefaults.colors(
                focusedTextColor = darkBlue,
                unfocusedTextColor = darkBlue,
                focusedContainerColor = secondary,
                unfocusedContainerColor = secondary,
            )
        )

        CustomButton(
            enabled = uiState.isValid,
            onClick = {
                    viewModel.saveCampaign {
                        navHostController.popBackStack()
                    }

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(Res.string.save_button))
        }
        if (uiState.showDeleteButton) {
            CustomButton(
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary,
                    contentColor = primaryDark
                ),
                onClick = { deleteDialogDisplay = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(Res.string.delete_button))
            }
        }

    }
}