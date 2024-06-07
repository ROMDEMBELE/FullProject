package ui.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.darkPrimary
import ui.lightGray

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> DropDownTextField(
    value: T,
    label: String,
    list: List<T>,
    modifier: Modifier = Modifier,
    onSelected: (T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        TextField(
            value = value.toString(),
            onValueChange = { },
            readOnly = true,
            shape = RoundedCornerShape(10.dp),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            label = { Text(text = label) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                backgroundColor = lightGray,
                unfocusedIndicatorColor = Color.Transparent,
                leadingIconColor = darkPrimary,
                focusedTrailingIconColor = darkPrimary,
                focusedLabelColor = darkPrimary,
                unfocusedLabelColor = darkPrimary,
                trailingIconColor = darkPrimary,
                placeholderColor = Color.Transparent
            ),
            modifier = modifier.fillMaxWidth()
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
                        expanded = false
                        onSelected(it)
                    },
                ) {
                    Text(text = it.toString())
                }
            }
        }
    }
}