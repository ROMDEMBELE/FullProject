package ui.character.edit

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
import org.dembeyo.shared.resources.minus_circle
import org.dembeyo.shared.resources.plus_circle
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import ui.BigBold
import ui.MediumBold
import ui.SmallBold
import ui.composable.CustomButton
import ui.composable.CustomTextField
import ui.composable.DropDownTextField
import ui.darkBlue
import ui.darkPrimary
import ui.lightGray
import ui.primary
import ui.roundCornerShape
import ui.secondary

class EditCharacterScreen(val id: Long? = null) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()
        val viewModel: EditCharacterViewModel = koinInject()
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
                        viewModel.deleteCharacter()
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

        LaunchedEffect(uiState.isReady) {
            if (id != null && uiState.isReady) {
                viewModel.loadCharacterToEdit(id)
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
                    textFieldValue = uiState.playerName,
                    onTextChange = { viewModel.updatePlayerName(it) },
                    placeholder = "Player Name",
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(Modifier.height(8.dp))

                CustomTextField(
                    textFieldValue = uiState.characterName,
                    onTextChange = { viewModel.updateCharacterName(it) },
                    placeholder = "Character Name",
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                CustomTextField(
                    textFieldValue = uiState.characterClass,
                    onTextChange = { viewModel.updateCharacterClass(it) },
                    placeholder = "Character Class",
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                DropDownTextField(
                    value = uiState.characterBackground,
                    display = { this?.name ?: "" },
                    label = "Background",
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
                    label = "Species",
                    list = uiState.species.values.toList()
                ) {
                    if (it != null) {
                        viewModel.updateCharacterSpecies(it)
                    }
                }

                Divider(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
                    color = darkPrimary,
                    thickness = 3.dp
                )

                Text("Character Statistics", style = BigBold)

                Spacer(Modifier.height(8.dp))

                CounterSelector("Level", minimum = 1, maximum = 20, value = uiState.level) {
                    viewModel.updateLevel(it)
                }

                Spacer(Modifier.height(8.dp))

                CounterSelector(label = "Armor Class", value = uiState.armorClass, maximum = 30) {
                    viewModel.updateArmorClass(it)
                }

                Spacer(Modifier.height(8.dp))

                CounterSelector(
                    label = "Hit Point", minimum = 1, maximum = 999, value = uiState.hitPoint
                ) {
                    viewModel.updateHitPoint(it)
                }

                Spacer(Modifier.height(8.dp))

                CounterSelector(label = "Spell Save", value = uiState.spellSave, maximum = 30) {
                    viewModel.updateSpellSave(it)
                }

                Divider(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
                    color = darkPrimary,
                    thickness = 3.dp
                )

                Text("Character Abilities", style = BigBold)

                Spacer(Modifier.height(8.dp))

                uiState.abilities.forEach { (ability, value) ->
                    CounterSelector(ability.fullName, value = value) {
                        viewModel.updateAbilityScores(ability, it)
                    }
                    Spacer(Modifier.height(8.dp))
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
                            viewModel.saveCharacter()
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

    @Composable
    fun CounterSelector(
        label: String,
        value: Int,
        minimum: Int = 0,
        maximum: Int = 20,
        step: Int = 1,
        onChange: (Int) -> Unit,
    ) {
        Surface(shape = roundCornerShape, color = darkBlue) {
            Row(
                Modifier.fillMaxWidth().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    modifier = Modifier.size(20.dp).aspectRatio(1f),
                    onClick = { onChange(if (value - step >= minimum) value - step else minimum) },
                    enabled = value > minimum,
                ) {
                    Image(
                        painterResource(Res.drawable.minus_circle),
                        null,
                        colorFilter = ColorFilter.tint(if (value > minimum) Color.White else lightGray)
                    )
                }
                Text(text = "min : $minimum", style = SmallBold, modifier = Modifier.alpha(.5f))
                Text(
                    text = "$label : $value",
                    style = MediumBold,
                    modifier = Modifier.width(180.dp)
                )
                Text(text = "max : $maximum", style = SmallBold, modifier = Modifier.alpha(.5f))
                IconButton(
                    modifier = Modifier.size(20.dp).aspectRatio(1f),
                    onClick = { onChange(if (value + step <= maximum) value + step else maximum) },
                    enabled = value < maximum,
                ) {
                    Image(
                        painterResource(Res.drawable.plus_circle),
                        null,
                        colorFilter = ColorFilter.tint(if (value < maximum) Color.White else lightGray),
                    )
                }
            }
        }
    }
}