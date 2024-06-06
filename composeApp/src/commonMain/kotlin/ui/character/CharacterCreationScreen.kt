package ui.character

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import domain.DndClass
import fullproject.composeapp.generated.resources.Res
import fullproject.composeapp.generated.resources.minus_circle
import fullproject.composeapp.generated.resources.plus_circle
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import ui.darkBlue
import ui.lightGray
import ui.mediumBoldWhite

class CharacterCreationScreen() : Screen {
    @Composable
    override fun Content() {
        val viewModel: CharacterViewModel = koinInject()
        val scope = rememberCoroutineScope()
        val uiState by viewModel.uiState.collectAsState()
        var classes by remember { mutableStateOf(emptyList<DndClass>()) }

        scope.launch {
            classes = viewModel.getClasses()
        }

        LazyColumn(modifier = Modifier.padding(16.dp)) {
            item {
                TextField(
                    value = uiState.name,
                    onValueChange = { viewModel.updateName(it) },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                if (classes.isNotEmpty()) {
                    DropDownTextField("Classes", classes.map { it.name }) { value ->
                        viewModel.updateClass(classes.find { it.name == value })
                    }
                }

                Spacer(Modifier.height(12.dp))

                TextField(
                    value = uiState.age,
                    onValueChange = { viewModel.updateAge(it) },
                    label = { Text("Age") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                CounterSelector("Level") {
                    viewModel.updateLevel(it)
                }

                Spacer(Modifier.height(12.dp))

                uiState.abilities.forEach { (ability, modifier) ->
                    CounterSelector(ability.fullName, defaultValue = modifier) {
                        viewModel.updateAbilityScores(ability, it)
                    }
                    Spacer(Modifier.height(12.dp))
                }

                Button(
                    enabled = uiState.isValid,
                    onClick = {
                        viewModel.saveCharacter()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save")
                }
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun DropDownTextField(label: String, list: List<String>, onSelected: (String) -> Unit) {
        var expanded by remember { mutableStateOf(false) }
        var selectionOption by remember { mutableStateOf(list[0]) }
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            TextField(
                value = selectionOption,
                onValueChange = { },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                label = { Text(text = label) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier.fillMaxWidth()
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                list.forEach {
                    DropdownMenuItem(
                        onClick = {
                            selectionOption = it
                            expanded = false
                            onSelected(it)
                        },
                    ) {
                        Text(text = it)
                    }
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
                    style = mediumBoldWhite,
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