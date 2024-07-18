package ui.magicItem.details

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.ornament
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import ui.composable.CustomAnimatedPlaceHolder
import ui.composable.MediumBoldSecondary
import ui.composable.TaperedRule
import ui.composable.darkBlue
import ui.composable.darkPrimary
import ui.composable.magicItemTitle
import ui.composable.secondary


class MagicItemDetailsScreen(private val index: String) : Screen {

    override val key: ScreenKey
        get() = uniqueScreenKey

    @Composable
    override fun Content() {
        val infiniteTransition = rememberInfiniteTransition()
        val viewModel: MagicItemDetailsViewModel = koinInject()
        val uiState by viewModel.uiState.collectAsState()

        LaunchedEffect(index) {
            viewModel.fetchMagicItem(index)
        }


        AnimatedContent(uiState, transitionSpec = { fadeIn().togetherWith(fadeOut()) }) { state ->
            val magicItem = state.magicItem
            if (state.isReady && magicItem != null) {
                val rotation by infiniteTransition.animateFloat(
                    0f,
                    360f,
                    infiniteRepeatable(tween(50000, easing = LinearEasing), RepeatMode.Restart)
                )
                val brush = Brush.horizontalGradient(listOf(secondary, magicItem.rarity.color))

                Column(Modifier.fillMaxSize().background(brush).padding(8.dp)) {
                    Box(Modifier.weight(0.2f)) {
                        Image(
                            // TODO change ornament in the background
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
                            text = magicItem.name,
                            style = magicItemTitle,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    TaperedRule(Modifier.padding(vertical = 8.dp), darkBlue)

                    Text(
                        text = magicItem.rarity.text,
                        color = darkBlue,
                        style = MediumBoldSecondary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(magicItem.rarity.color)
                            .padding(4.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = magicItem.category.name,
                        color = secondary,
                        style = MediumBoldSecondary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(darkBlue)
                            .padding(4.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    if (magicItem.hasAttunement) {
                        Text(
                            text = "Requires Attunement",
                            color = secondary,
                            style = MediumBoldSecondary,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(darkPrimary)
                                .padding(4.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

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
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            } else if (state.isReady) {

            } else {
                CustomAnimatedPlaceHolder()
            }
        }
    }
}