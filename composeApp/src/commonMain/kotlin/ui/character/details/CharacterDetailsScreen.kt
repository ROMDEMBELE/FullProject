package ui.character.details

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.armor_class
import org.dembeyo.shared.resources.character_name
import org.dembeyo.shared.resources.delete_button
import org.dembeyo.shared.resources.hit_points
import org.jetbrains.compose.resources.stringResource
import ui.color
import ui.composable.CustomAlertDialog
import ui.composable.CustomAnimatedPlaceHolder
import ui.composable.CustomButton
import ui.composable.PropertyLine
import ui.composable.TaperedRule
import ui.composable.primary
import ui.composable.secondary
import ui.monster.details.AbilityChip
import ui.stringRes

@Composable
fun CharacterDetailsScreen(
    navHostController: NavHostController,
    viewModel: CharacterDetailsViewModel
) {

    val uiState by viewModel.state.collectAsState()
    var deleteDialogDisplay by remember { mutableStateOf(false) }

    AnimatedVisibility(deleteDialogDisplay) {
        CustomAlertDialog(
            onDismiss = { deleteDialogDisplay = false },
            title = "Delete Character",
            content = "Are you sure you want to delete this character?",
            confirmText = stringResource(Res.string.delete_button),
            onConfirm = {
                deleteDialogDisplay = false
                viewModel.deleteCharacter {
                    navHostController.popBackStack()
                }
            })
    }

    AnimatedContent(uiState, transitionSpec = { fadeIn().togetherWith(fadeOut()) }) { state ->
        if (!state.isReady) {
            CustomAnimatedPlaceHolder()
        } else {
            val gradient = Brush.verticalGradient(
                colors = listOf(secondary, state.characterLevel.color()),
            )
            Column(
                modifier = Modifier.background(gradient).padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                PropertyLine(Res.string.character_name, state.characterName)

                TaperedRule()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    state.abilities.entries.forEach { (ability, value) ->
                        AbilityChip(stringResource(ability.stringRes()), value)
                    }
                }

                PropertyLine(Res.string.armor_class, state.armorClass.toString())

                PropertyLine(Res.string.hit_points, state.hitPoint.toString())

                AnimatedVisibility(uiState.canBeDeleted) {
                    CustomButton(
                        onClick = { deleteDialogDisplay = true },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primary,
                            contentColor = secondary
                        ),
                    ) {
                        Text(stringResource(Res.string.delete_button))
                    }
                }
            }
        }
    }
}