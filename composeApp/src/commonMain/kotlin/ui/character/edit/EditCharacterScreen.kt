package ui.character.edit

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.launch
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.armor_class
import org.dembeyo.shared.resources.background
import org.dembeyo.shared.resources.character_class
import org.dembeyo.shared.resources.character_name
import org.dembeyo.shared.resources.delete_button
import org.dembeyo.shared.resources.edit_character_abilities
import org.dembeyo.shared.resources.edit_character_information
import org.dembeyo.shared.resources.edit_character_statistics
import org.dembeyo.shared.resources.hit_points
import org.dembeyo.shared.resources.level
import org.dembeyo.shared.resources.player_name
import org.dembeyo.shared.resources.save_button
import org.dembeyo.shared.resources.species
import org.dembeyo.shared.resources.spell_save
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import ui.composable.CounterSelector
import ui.composable.CustomAlertDialog
import ui.composable.CustomAnimatedPlaceHolder
import ui.composable.CustomButton
import ui.composable.CustomTextField
import ui.composable.DropDownTextField
import ui.composable.MediumBoldDarkBlue
import ui.composable.SliderSelector
import ui.composable.TaperedRule
import ui.composable.darkPrimary
import ui.composable.primary
import ui.composable.secondary

class EditCharacterScreen(val id: Long? = null) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()
        val viewModel: EditCharacterViewModel = koinInject()
        val uiState by viewModel.uiState.collectAsState()
        var deleteDialogDisplay by remember { mutableStateOf(false) }

        AnimatedVisibility(deleteDialogDisplay) {
            CustomAlertDialog(
                onDismiss = { deleteDialogDisplay = false },
                title = "Delete Character",
                content = "Are you sure you want to delete this character?",
                confirmText = stringResource(Res.string.delete_button),
                onConfirm = {
                    deleteDialogDisplay = false
                    viewModel.deleteCharacter()
                    navigator.pop()

                })
        }

        LaunchedEffect(uiState.isReady) {
            if (id != null && uiState.isReady) {
                viewModel.loadCharacterToEdit(id)
            }
        }

        CustomAnimatedPlaceHolder()

        AnimatedVisibility(uiState.isReady, enter = fadeIn(), exit = fadeOut()) {
            val gradient = Brush.verticalGradient(
                colors = listOf(secondary, uiState.level.color),
            )
            LazyColumn(
                modifier = Modifier.background(gradient).padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Text(
                        stringResource(Res.string.edit_character_information),
                        style = MediumBoldDarkBlue
                    )

                    Spacer(Modifier.height(8.dp))

                    CustomTextField(
                        textFieldValue = uiState.playerName,
                        onTextChange = { viewModel.updatePlayerName(it) },
                        placeholder = stringResource(Res.string.player_name),
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Spacer(Modifier.height(8.dp))

                    CustomTextField(
                        textFieldValue = uiState.characterName,
                        onTextChange = { viewModel.updateCharacterName(it) },
                        placeholder = stringResource(Res.string.character_name),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(8.dp))

                    CustomTextField(
                        textFieldValue = uiState.characterClass,
                        onTextChange = { viewModel.updateCharacterClass(it) },
                        placeholder = stringResource(Res.string.character_class),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(8.dp))

                    DropDownTextField(
                        value = uiState.characterBackground,
                        display = { this?.name ?: "" },
                        label = stringResource(Res.string.background),
                        list = uiState.backgrounds.values.toList()
                    ) {
                        if (it != null) {
                            viewModel.updateCharacterBackground(it)
                        }
                    }

                    Spacer(Modifier.height(8.dp))

                    DropDownTextField(
                        value = uiState.characterSpecies,
                        display = { this?.fullName ?: "" },
                        label = stringResource(Res.string.species),
                        list = uiState.species.values.toList()
                    ) {
                        if (it != null) {
                            viewModel.updateCharacterSpecies(it)
                        }
                    }

                    Spacer(Modifier.height(8.dp))

                    Text(
                        stringResource(Res.string.edit_character_statistics),
                        style = MediumBoldDarkBlue
                    )

                    Spacer(Modifier.height(8.dp))

                    CounterSelector(
                        stringResource(Res.string.level),
                        minimum = 1,
                        maximum = 20,
                        value = uiState.level.level
                    ) {
                        viewModel.updateLevel(it)
                    }

                    Spacer(Modifier.height(8.dp))

                    CounterSelector(
                        stringResource(Res.string.armor_class),
                        value = uiState.armorClass,
                        maximum = 30
                    ) {
                        viewModel.updateArmorClass(it)
                    }

                    Spacer(Modifier.height(8.dp))

                    SliderSelector(
                        label = stringResource(Res.string.hit_points),
                        value = uiState.hitPoint,
                        minimum = 1,
                        maximum = 999,
                    ) {
                        viewModel.updateHitPoint(it)
                    }

                    Spacer(Modifier.height(8.dp))

                    CounterSelector(
                        stringResource(Res.string.spell_save),
                        value = uiState.spellSave,
                        maximum = 30
                    ) {
                        viewModel.updateSpellSave(it)
                    }

                    Spacer(Modifier.height(8.dp))

                    Text(stringResource(Res.string.edit_character_abilities), style = MediumBoldDarkBlue)

                    Spacer(Modifier.height(8.dp))

                    uiState.abilities.forEach { (ability, value) ->
                        val abilityName = stringResource(ability.stringRes)
                        CounterSelector(abilityName, value = value) {
                            viewModel.updateAbilityScores(ability, it)
                        }
                        Spacer(Modifier.height(8.dp))
                    }

                    TaperedRule(color = darkPrimary)

                    CustomButton(
                        enabled = uiState.isValid,
                        onClick = {
                            scope.launch {
                                viewModel.save()
                                navigator.pop()
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(Res.string.save_button))
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
                            Text(stringResource(Res.string.delete_button))
                        }
                    }
                }
            }
        }
    }
}