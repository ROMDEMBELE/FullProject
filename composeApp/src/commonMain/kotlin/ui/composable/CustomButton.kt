package ui.composable

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.darkGray
import ui.darkPrimary
import ui.lightGray
import ui.secondary

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = darkPrimary,
        contentColor = secondary,
        disabledContentColor = darkGray,
        disabledBackgroundColor = lightGray
    ),
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        enabled = enabled,
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        colors = colors,
        modifier = modifier,
        content = content
    )
}