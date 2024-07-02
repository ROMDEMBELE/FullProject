package ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.cancel_button
import org.dembeyo.shared.resources.confirm_button
import org.jetbrains.compose.resources.stringResource

@Composable
fun CustomAlertDialog(
    title: String,
    cancellable: Boolean = true,
    content: String,
    onDismiss: () -> Unit,
    confirmText: String = stringResource(Res.string.confirm_button),
    onConfirm: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnClickOutside = cancellable,
            dismissOnBackPress = cancellable,
        )
    ) {
        Surface(color = secondary, shape = roundCornerShape) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    title, modifier = Modifier.fillMaxWidth(),
                    color = darkPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    content, modifier = Modifier.fillMaxWidth(),
                    color = darkPrimary,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                )

                Spacer(Modifier.height(16.dp))

                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    CustomButton(
                        modifier = Modifier.weight(0.3f),
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = primary,
                            contentColor = darkPrimary
                        )
                    ) {
                        Text(stringResource(Res.string.cancel_button))
                    }

                    Spacer(Modifier.weight(0.3f))

                    CustomButton(
                        modifier = Modifier.weight(0.3f),
                        onClick = onConfirm
                    ) {
                        Text(confirmText)
                    }
                }
            }
        }
    }
}