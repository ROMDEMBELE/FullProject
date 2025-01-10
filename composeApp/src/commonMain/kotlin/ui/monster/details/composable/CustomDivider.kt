package ui.monster.details

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.composable.orange

@Composable
fun CustomDivider() {
    Divider(
        color = orange, thickness = 5.dp, modifier = Modifier.padding(vertical = 8.dp)
    )
}