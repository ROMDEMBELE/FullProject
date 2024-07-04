package ui.composable

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.d20
import org.dembeyo.shared.resources.loading
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun CustomAnimatedPlaceHolder(
    backgroundColor: Color = darkBlue,
    contentColor: Color = secondary
) {
    val rotationAnimation = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        rotationAnimation.animateTo(360f, animationSpec = infiniteRepeatable(tween(5000)))
    }
    Column(
        Modifier.fillMaxSize().background(backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(stringResource(Res.string.loading), style = screenTitle(contentColor))
        Spacer(Modifier.height(36.dp))
        Image(
            painter = painterResource(Res.drawable.d20),
            colorFilter = ColorFilter.tint(contentColor),
            contentDescription = "loading",
            modifier = Modifier.size(100.dp).graphicsLayer {
                rotationZ = rotationAnimation.value
            }
        )
    }
}