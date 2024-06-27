package ui.settings.edit

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.launch
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.ancient
import org.dembeyo.shared.resources.menu_character
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import ui.BigBold
import ui.composable.CounterSelector
import ui.composable.CustomButton
import ui.composable.CustomTextField
import ui.darkPrimary
import ui.primary
import ui.roundCornerShape
import ui.secondary

class EditCampaignScreen(val id: Long? = null) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()
        val viewModel: EditCampaignViewModel = koinInject()
        val uiState by viewModel.uiState.collectAsState()
        var deleteDialogDisplay by remember { mutableStateOf(false) }

        AnimatedVisibility(deleteDialogDisplay) {
            AlertDialog(
                onDismissRequest = { deleteDialogDisplay = false },
                title = { Text("Delete Character") },
                text = { Text("Are you sure you want to delete this character?") },
                confirmButton = {
                    TextButton(onClick = {
                        deleteDialogDisplay = false
                        // TODO viewModel.deleteCharacter()
                        navigator.pop()
                    }) {
                        Text("Delete", color = primary)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { deleteDialogDisplay = false }) {
                        Text("Cancel", color = darkPrimary)
                    }
                },
                backgroundColor = secondary,
                contentColor = darkPrimary,
                shape = roundCornerShape
            )
        }

        LaunchedEffect(id) {
            if (id != null) {
                viewModel.loadCampaign(id)
            }
        }

        LazyColumn(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = stringResource(Res.string.menu_character),
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 40.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(Res.font.ancient)),
                    color = darkPrimary
                )

                Divider(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
                    color = darkPrimary,
                    thickness = 3.dp
                )

                Text("Character Information", style = BigBold)

                Spacer(Modifier.height(8.dp))

                CustomTextField(
                    textFieldValue = uiState.name,
                    onTextChange = { viewModel.updateName(it) },
                    placeholder = "Player Name",
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(Modifier.height(8.dp))

                TextField(
                    value = uiState.description,
                    onValueChange = { viewModel.updateDescription(it) },
                    placeholder = { Text("Description") },
                    modifier = Modifier.fillMaxWidth().height(200.dp),
                    singleLine = false,
                    maxLines = 30,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = secondary,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        textColor = darkPrimary
                    )
                )

                Spacer(Modifier.height(8.dp))

                CounterSelector("Progress", minimum = 1, maximum = 100, value = uiState.progress) {
                    viewModel.updateProgress(it)
                }

                Divider(
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 4.dp),
                    color = darkPrimary,
                    thickness = 3.dp
                )

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
                    Text("Save")
                }

                AnimatedVisibility(uiState.canBeDeleted) {
                    CustomButton(
                        onClick = { deleteDialogDisplay = true },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = primary,
                            contentColor = secondary
                        ),
                    ) {
                        Text("Delete")
                    }
                }
            }
        }
    }
}