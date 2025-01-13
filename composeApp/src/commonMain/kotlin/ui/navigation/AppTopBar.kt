package ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.adventure
import org.dembeyo.shared.resources.ancient
import org.dembeyo.shared.resources.castle_empty
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import ui.composable.darkBlue
import ui.composable.primary

@Composable
fun AppTopBar(title: String, onMenuClick: () -> Unit) {

    Box(Modifier.fillMaxWidth().background(darkBlue)) {
        IconButton(
            modifier = Modifier.align(Alignment.CenterStart),
            onClick = onMenuClick
        ) {
            Image(
                painter = painterResource(Res.drawable.adventure),
                contentDescription = null,
                colorFilter = ColorFilter.tint(primary),
                modifier = Modifier.size(24.dp).aspectRatio(1f)
            )
        }
        Text(
            modifier = Modifier.fillMaxWidth().align(Alignment.Center),
            text = title,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(Res.font.ancient)),
            color = primary,
        )
        IconButton(
            enabled = true,
            modifier = Modifier.align(Alignment.CenterEnd),
            onClick = {
                // TODO navigate to Campaign Screen
            }) {
            Image(
                painter = painterResource(Res.drawable.castle_empty),
                contentDescription = null,
                colorFilter = ColorFilter.tint(primary),
                modifier = Modifier.size(24.dp).aspectRatio(1f)
            )
        }
    }
}