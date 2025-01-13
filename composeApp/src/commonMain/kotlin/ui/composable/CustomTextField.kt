package ui.composable

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

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
    OutlinedTextField(
        shape = RoundedCornerShape(8.dp),
        value = textFieldValue,
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        label = { Text(placeholder) },
        leadingIcon = leadingIcon,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = lightGray,
            focusedTrailingIconColor = darkPrimary,
            focusedLeadingIconColor = darkPrimary,
            focusedTextColor = darkBlue,
            cursorColor = darkPrimary,
            focusedLabelColor = darkPrimary,
            unfocusedLabelColor = darkPrimary,
            focusedPlaceholderColor = Color.Transparent
        ),
        onValueChange = onTextChange,
        modifier = modifier
    )
}