package ui.composable

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
    TextField(
        shape = RoundedCornerShape(8.dp),
        value = textFieldValue,
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        label = { Text(placeholder) },
        leadingIcon = leadingIcon,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = lightGray,
            disabledContainerColor = lightGrayLight,
            unfocusedContainerColor = lightGray,
            focusedTextColor = primaryDark,
            unfocusedTextColor = lightGrayDark,
            disabledTextColor = lightGrayDark,
            disabledLeadingIconColor = lightGrayDark,
            unfocusedLeadingIconColor = lightGrayDark,
            focusedLeadingIconColor = primaryDark,
            unfocusedLabelColor = lightGrayDark,
            disabledLabelColor = lightGrayDark,
            focusedLabelColor = primaryDark,
            cursorColor = primaryDark,
            focusedSupportingTextColor = Color.Transparent,
            unfocusedSupportingTextColor = Color.Transparent,
            disabledSupportingTextColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        onValueChange = onTextChange,
        modifier = modifier
    )
}