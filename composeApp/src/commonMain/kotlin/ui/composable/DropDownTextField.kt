package ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import domain.model.monster.Challenge
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
                .border(if (expanded) 1.dp else 0.dp, darkPrimary, RoundedCornerShape(10.dp))
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
            modifier = Modifier.background(lightGray).fillMaxWidth(0.3f),
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
                        text = it.toString(),
                        fontSize = 13.sp,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}

fun Pair<Challenge, Int>.toString(): String = "CR ${first.rating} ($second)"