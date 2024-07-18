package ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> DropDownTextField(
    value: T,
    display: T.() -> String,
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
            value = value.display(),
            onValueChange = { },
            readOnly = true,
            shape = RoundedCornerShape(8.dp),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            label = { Text(text = label) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                backgroundColor = lightGray,
                unfocusedIndicatorColor = Color.Transparent,
                leadingIconColor = darkPrimary,
                textColor = darkBlue,
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
            },
            modifier = Modifier.background(lightGray).fillMaxWidth(0.5f),
            offset = DpOffset(250.dp, 350.dp),
        ) {
            list.forEach {
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onSelected(it)
                    },
                    modifier = Modifier.fillMaxWidth().height(20.dp),
                ) {
                    Text(
                        text = it.display(),
                        fontSize = 13.sp,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}