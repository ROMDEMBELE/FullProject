package ui.composable

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import ui.darkBlue
import ui.darkPrimary
import ui.lightGray
import ui.roundCornerShape

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    textFieldValue: TextFieldValue,
    keyboardType: KeyboardType = KeyboardType.Text,
    onTextChange: (TextFieldValue) -> Unit,
    placeholder: String,
    enabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    TextField(
        shape = roundCornerShape,
        value = textFieldValue,
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
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