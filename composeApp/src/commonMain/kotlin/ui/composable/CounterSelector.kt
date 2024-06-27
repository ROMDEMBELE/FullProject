package ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.minus_circle
import org.dembeyo.shared.resources.plus_circle
import org.jetbrains.compose.resources.painterResource
import ui.MediumBold
import ui.SmallBold
import ui.darkBlue
import ui.lightGray
import ui.roundCornerShape


@Composable
fun CounterSelector(
    label: String,
    value: Int,
    minimum: Int = 0,
    maximum: Int = 20,
    step: Int = 1,
    onChange: (Int) -> Unit,
) {
    Surface(shape = roundCornerShape, color = darkBlue) {
        Row(
            Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                modifier = Modifier.size(20.dp).aspectRatio(1f),
                onClick = { onChange(if (value - step >= minimum) value - step else minimum) },
                enabled = value > minimum,
            ) {
                Image(
                    painterResource(Res.drawable.minus_circle),
                    null,
                    colorFilter = ColorFilter.tint(if (value > minimum) Color.White else lightGray)
                )
            }
            Text(text = "min : $minimum", style = SmallBold, modifier = Modifier.alpha(.5f))
            Text(
                text = "$label : $value",
                style = MediumBold,
                modifier = Modifier.width(180.dp)
            )
            Text(text = "max : $maximum", style = SmallBold, modifier = Modifier.alpha(.5f))
            IconButton(
                modifier = Modifier.size(20.dp).aspectRatio(1f),
                onClick = { onChange(if (value + step <= maximum) value + step else maximum) },
                enabled = value < maximum,
            ) {
                Image(
                    painterResource(Res.drawable.plus_circle),
                    null,
                    colorFilter = ColorFilter.tint(if (value < maximum) Color.White else lightGray),
                )
            }
        }
    }
}