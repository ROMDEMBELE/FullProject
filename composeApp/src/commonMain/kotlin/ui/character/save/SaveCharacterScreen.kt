package ui.character.save

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import domain.model.Ability
import domain.model.character.CharacterClass
import kotlinx.coroutines.launch
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.armor_class
import org.dembeyo.shared.resources.character_class
import org.dembeyo.shared.resources.character_name
import org.dembeyo.shared.resources.delete_button
import org.dembeyo.shared.resources.drop_down_option_empty
import org.dembeyo.shared.resources.edit_character_abilities
import org.dembeyo.shared.resources.edit_character_information
import org.dembeyo.shared.resources.edit_character_statistics
import org.dembeyo.shared.resources.hit_points
import org.dembeyo.shared.resources.level
import org.dembeyo.shared.resources.monster_senses_passive_perception
import org.dembeyo.shared.resources.save_button
import org.jetbrains.compose.resources.stringResource
import ui.color
import ui.composable.CounterSelector
import ui.composable.CustomAlertDialog
import ui.composable.CustomAnimatedPlaceHolder
import ui.composable.CustomButton
import ui.composable.CustomTextField
import ui.composable.DropDownTextField
import ui.composable.MediumBoldDarkBlue
import ui.composable.SliderSelector
import ui.composable.TaperedRule
import ui.composable.primary
import ui.composable.primaryDark
import ui.composable.secondary
import ui.stringRes

@Composable
fun SaveCharacterScreen(
    navHostController: NavHostController,
    viewModel: SaveCharacterViewModel
) {

    val scope = rememberCoroutineScope()
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
                colors = listOf(secondary, uiState.level.color()),
            )
            LazyColumn(
                modifier = Modifier.background(gradient).padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Text(
                        stringResource(Res.string.edit_character_information),
                        style = MediumBoldDarkBlue
                    )

                    CustomTextField(
                        textFieldValue = state.characterName,
                        onTextChange = { viewModel.onNameChange(it) },
                        placeholder = stringResource(Res.string.character_name),
                        modifier = Modifier.fillMaxWidth()
                    )

                    DropDownTextField(
                        label = stringResource(Res.string.character_class),
                        selectedValue = state.characterClass,
                        valueToString = {
                            if (it == null) stringResource(Res.string.drop_down_option_empty) else stringResource(
                                it.stringRes()
                            )
                        },
                        onSelected = viewModel::onClassChange,
                        values = CharacterClass.entries
                    )

                    Text(
                        stringResource(Res.string.edit_character_statistics),
                        style = MediumBoldDarkBlue
                    )

                    CounterSelector(
                        stringResource(Res.string.level),
                        minimum = 1,
                        maximum = 20,
                        value = state.level.level,
                        onChange = viewModel::onLevelChange
                    )


                    CounterSelector(
                        stringResource(Res.string.armor_class),
                        value = state.armorClass,
                        maximum = 30,
                        onChange = viewModel::onArmorClassChange
                    )

                    CounterSelector(
                        stringResource(Res.string.monster_senses_passive_perception),
                        value = state.passivePerception,
                        maximum = 30,
                        onChange = viewModel::onPassivePerceptionChange
                    )

                    SliderSelector(
                        label = stringResource(Res.string.hit_points),
                        value = state.hitPoint,
                        minimum = 1,
                        maximum = 999,
                        onChange = viewModel::onHitPointChange
                    )

                    Text(
                        stringResource(Res.string.edit_character_abilities),
                        style = MediumBoldDarkBlue
                    )

                    CounterSelector(
                        stringResource(Ability.INT.stringRes()),
                        value = state.intelligence,
                        onChange = viewModel::onIntelligenceChange
                    )

                    CounterSelector(
                        stringResource(Ability.WIS.stringRes()),
                        value = state.wisdom,
                        onChange = viewModel::onWisdomChange
                    )

                    CounterSelector(
                        stringResource(Ability.STR.stringRes()),
                        value = state.strength,
                        onChange = viewModel::onStrengthChange
                    )

                    CounterSelector(
                        stringResource(Ability.DEX.stringRes()),
                        value = state.dexterity,
                        onChange = viewModel::onDexterityChange
                    )

                    CounterSelector(
                        stringResource(Ability.CON.stringRes()),
                        value = state.constitution,
                        onChange = viewModel::onConstitutionChange
                    )

                    CounterSelector(
                        stringResource(Ability.CHA.stringRes()),
                        value = state.charisma,
                        onChange = viewModel::onCharismaChange
                    )

                    TaperedRule(color = primaryDark)

                    CustomButton(
                        enabled = uiState.isValid,
                        onClick = {
                            scope.launch {
                                viewModel.saveCharacter {
                                    navHostController.popBackStack()
                                }
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
}