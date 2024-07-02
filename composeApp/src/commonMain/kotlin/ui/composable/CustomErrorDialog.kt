package ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.confirm_button
import org.jetbrains.compose.resources.stringResource

@Composable
fun CustomErrorDialog(
    title: String,
    content: String,
    onClose: () -> Unit
) {
    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = false,
        )
    ) {
        Surface(color = darkPrimary, shape = roundCornerShape) {
            Column(
                modifier = Modifier.padding(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    title, modifier = Modifier.fillMaxWidth(),
                    color = secondary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    content, modifier = Modifier.fillMaxWidth(),
                    color = secondary,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                )

                Spacer(Modifier.height(16.dp))

                CustomButton(
                    onClick = onClose,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = secondary,
                        contentColor = darkPrimary
                    )
                ) {
                    Text(stringResource(Res.string.confirm_button))
                }
            }
        }
    }
}