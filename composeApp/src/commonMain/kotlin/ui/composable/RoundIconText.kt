package ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun RoundIconText(
    drawable: DrawableResource? = null,
    modifier: Modifier = Modifier,
    text: String? = null,
    iconColor: Color = darkPrimary,
    backgroundColor: Color = darkGray
) {
    Surface(shape = CircleShape, color = backgroundColor, modifier = modifier.size(40.dp)) {
        Box {
            if (drawable != null) {
                Image(
                    painter = painterResource(drawable),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(iconColor),
                    modifier = Modifier.alpha(0.7f).size(30.dp).align(Alignment.Center)
                        .aspectRatio(1f)
                )
            }
            if (text != null) {
                Text(
                    text = text,
                    style = SmallBoldSecondary,
                    color = iconColor,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

        }
    }
}