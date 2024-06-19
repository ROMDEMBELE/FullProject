package ui.player

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import domain.model.character.CharacterClass
import kotlinx.coroutines.launch
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.ancient
import org.dembeyo.shared.resources.minus_circle
import org.dembeyo.shared.resources.plus_circle
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import ui.MediumBold
import ui.composable.CustomTextField
import ui.darkBlue
import ui.darkGray
import ui.darkPrimary
import ui.lightGray
import ui.secondary

class EditPlayerStatScreen(val id: Int? = null) : Screen {
    @Composable
    override fun Content() {
        val viewModel: PlayerViewModel = koinInject()
        val scope = rememberCoroutineScope()
        val uiState by viewModel.uiState.collectAsState()
        var classes by remember { mutableStateOf(emptyList<CharacterClass>()) }

        scope.launch {
            classes = viewModel.getClasses()
        }

        LazyColumn(modifier = Modifier.padding(8.dp)) {
            item {
                Text(
                    "Player Character",
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

                Divider(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
                    color = darkPrimary,
                    thickness = 3.dp
                )

                CounterSelector("Level", minus = 1, maximum = 20, defaultValue = 1) {
                    viewModel.updateLevel(it)
                }

                Spacer(Modifier.height(8.dp))

                CounterSelector("Armor Class") {
                    viewModel.updateArmorClass(it)
                }

                Spacer(Modifier.height(8.dp))

                CounterSelector("Hit Point", minus = 1, maximum = 999) {
                    viewModel.updateHitPoint(it)
                }

                Spacer(Modifier.height(8.dp))

                CounterSelector("Spell Save") {
                    viewModel.updateSpellSave(it)
                }

                Divider(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
                    color = darkPrimary,
                    thickness = 3.dp
                )

                uiState.abilities.forEach { (ability, modifier) ->
                    CounterSelector(ability.fullName, defaultValue = modifier) {
                        viewModel.updateAbilityScores(ability, it)
                    }
                    Spacer(Modifier.height(8.dp))
                }

                Divider(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
                    color = darkPrimary,
                    thickness = 3.dp
                )

                Button(
                    enabled = uiState.isValid,
                    onClick = {},
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = darkPrimary,
                        contentColor = secondary,
                        disabledContentColor = darkGray,
                        disabledBackgroundColor = lightGray
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save")
                }
            }
        }
    }

    @Composable
    fun CounterSelector(
        label: String,
        defaultValue: Int = 10,
        minus: Int = 0,
        maximum: Int = 20,
        step: Int = 1,
        onChange: (Int) -> Unit,
    ) {
        var value by rememberSaveable { mutableStateOf(defaultValue) }
        Surface(shape = RoundedCornerShape(10.dp), color = darkBlue) {
            Box(Modifier.fillMaxWidth().height(50.dp).padding(12.dp)) {
                IconButton(
                    modifier = Modifier.align(Alignment.CenterStart),
                    onClick = {
                        if (value - step >= minus) value -= step else value = minus
                        onChange(value)
                    }, enabled = value > minus
                ) {
                    Image(
                        painterResource(Res.drawable.minus_circle),
                        null,
                        colorFilter = ColorFilter.tint(if (value > minus) Color.White else lightGray)
                    )
                }
                Text(
                    text = "$label : $value",
                    style = MediumBold,
                    modifier = Modifier.align(Alignment.Center)
                )
                IconButton(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    onClick = {
                        if (defaultValue + step <= maximum) value += step else value = maximum
                        onChange(value)
                    }, enabled = value < maximum
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