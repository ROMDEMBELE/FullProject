package ui.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.runtime.getValue
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
    visible: Boolean,
    backgroundColor: Color = darkBlue,
    contentColor: Color = secondary
) {
    val infiniteTransition = rememberInfiniteTransition()
    val degrees by infiniteTransition.animateFloat(
        0f, 360f, infiniteRepeatable(tween(3000, easing = LinearEasing))
    )
    AnimatedVisibility(visible, enter = fadeIn(), exit = fadeOut()) {
        Column(
            Modifier.fillMaxSize().background(backgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(stringResource(Res.string.loading), style = screenTitle(contentColor))
            Spacer(Modifier.height(24.dp))
            Image(
                painter = painterResource(Res.drawable.d20),
                colorFilter = ColorFilter.tint(contentColor),
                contentDescription = "loading",
                modifier = Modifier.size(180.dp).graphicsLayer {
                    rotationZ = degrees
                }
            )
        }
    }

}