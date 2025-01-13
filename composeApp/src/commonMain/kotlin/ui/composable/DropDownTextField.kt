package ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> DropDownTextField(
    selectedValue: T?,
    display: @Composable T?.() -> String,
    label: String,
    values: List<T?>,
    modifier: Modifier = Modifier,
    onSelected: (T?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        TextField(
            value = selectedValue.display(),
            onValueChange = { },
            readOnly = true,
            shape = RoundedCornerShape(8.dp),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            label = { Text(text = label) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                focusedContainerColor = lightGray,
                unfocusedIndicatorColor = Color.Transparent,
                focusedLeadingIconColor = darkPrimary,
                focusedTextColor = darkBlue,
                focusedTrailingIconColor = darkPrimary,
                focusedLabelColor = darkPrimary,
                unfocusedLabelColor = darkPrimary,
                unfocusedTrailingIconColor = darkPrimary,
                focusedPlaceholderColor = Color.Transparent
            ),
            modifier = modifier.fillMaxWidth()
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(lightGray)
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
        ) {
            values.forEach { item ->
                DropdownMenuItem(
                    text = { item.display() },
                    onClick = {
                        expanded = false
                        onSelected(item)
                    },
                )
            }
        }
    }
}