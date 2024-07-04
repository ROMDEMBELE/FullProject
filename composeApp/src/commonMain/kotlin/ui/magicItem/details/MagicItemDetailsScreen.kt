package ui.magicItem.details

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import domain.model.magicItem.MagicItem
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.ornament
import org.jetbrains.compose.resources.painterResource
import ui.composable.TaperedRule
import ui.composable.darkBlue
import ui.composable.monsterTitle
import ui.composable.secondary


class MagicItemDetailsScreen(private val magicItem: MagicItem) : Screen {

    override val key: ScreenKey
        get() = uniqueScreenKey

    @Composable
    override fun Content() {
        val infiniteTransition = rememberInfiniteTransition()

        val rotation by infiniteTransition.animateFloat(
            0f, 360f, infiniteRepeatable(tween(50000, easing = LinearEasing), RepeatMode.Restart)
        )

        val brush = Brush.horizontalGradient(listOf(secondary, magicItem.rarity.color))

        Column(Modifier.fillMaxSize().background(brush).padding(8.dp)) {
            Box(Modifier.weight(0.2f)) {
                Image(
                    painterResource(Res.drawable.ornament),
                    null,
                    modifier = Modifier
                        .wrapContentSize(unbounded = true, align = Alignment.Center)
                        .alpha(.2f)
                        .scale(0.5f)
                        .graphicsLayer {
                            rotationZ = rotation
                        },
                    colorFilter = ColorFilter.tint(darkBlue)
                )

                Text(
                    magicItem.name,
                    Modifier.align(Alignment.Center),
                    style = monsterTitle.copy(
                        color = darkBlue, shadow = Shadow(
                            color = magicItem.rarity.color,
                            offset = Offset(5f, 5f),
                            blurRadius = 12f
                        )
                    )
                )
            }

            TaperedRule(Modifier.padding(vertical = 8.dp), darkBlue)

            LazyColumn(
                Modifier.clip(RoundedCornerShape(8.dp))
                    .background(darkBlue)
                    .padding(8.dp)
                    .fillMaxWidth()
                    .weight(.6f)
            ) {
                items(magicItem.description) { text ->
                    Text(
                        text,
                        fontSize = 14.sp,
                        style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                        fontFamily = FontFamily.Serif,
                        color = secondary
                    )
                }
            }
        }
    }
}