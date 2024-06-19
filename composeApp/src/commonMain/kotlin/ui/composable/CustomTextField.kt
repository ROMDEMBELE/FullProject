package ui.composable

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import ui.darkBlue
import ui.darkPrimary
import ui.lightGray

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    textFieldValue: TextFieldValue,
    onTextChange: (TextFieldValue) -> Unit,
    placeholder: String,
    enabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    TextField(
        shape = RoundedCornerShape(20.dp),
        value = textFieldValue,
        enabled = enabled,
        label = { Text(placeholder) },
        leadingIcon = leadingIcon,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = lightGray,
            trailingIconColor = darkPrimary,
            leadingIconColor = darkPrimary,
            textColor = darkBlue,
            cursorColor = darkPrimary,
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent,
            focusedLabelColor = darkPrimary,
            unfocusedLabelColor = darkPrimary,
            placeholderColor = Color.Transparent
        ),
        onValueChange = onTextChange,
        modifier = modifier
    )
}