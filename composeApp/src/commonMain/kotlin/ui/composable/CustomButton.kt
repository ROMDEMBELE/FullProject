package ui.composable

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = primaryDark,
        contentColor = secondary,
        disabledContainerColor = lightGray,
        disabledContentColor = darkGray
    ),
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        enabled = enabled,
        onClick = onClick,
        shape = roundCornerShape,
        colors = colors,
        modifier = modifier,
        content = content
    )
}