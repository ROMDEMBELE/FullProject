package ui.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TaperedRule(modifier: Modifier = Modifier.padding(vertical = 4.dp)) {
    Divider(
        color = darkPrimary,
        thickness = 2.dp,
        modifier = modifier
    )
}