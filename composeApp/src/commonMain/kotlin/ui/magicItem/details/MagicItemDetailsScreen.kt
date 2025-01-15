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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.magic_item_category
import org.dembeyo.shared.resources.magic_item_cost
import org.dembeyo.shared.resources.magic_item_require_attunement
import org.dembeyo.shared.resources.magic_item_weight
import org.dembeyo.shared.resources.ornament
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.composable.CustomAnimatedPlaceHolder
import ui.composable.MediumBoldSecondary
import ui.composable.PropertyLine
import ui.composable.TaperedRule
import ui.composable.darkBlue
import ui.composable.magicItemTitle
import ui.composable.primaryDark
import ui.composable.secondary
import ui.getRarityColor
import ui.stringRes


@Composable
fun MagicItemDetailsScreen(viewModel: MagicItemDetailsViewModel) {

    val infiniteTransition = rememberInfiniteTransition()
    val uiState by viewModel.uiState.collectAsState()

    AnimatedContent(uiState, transitionSpec = { fadeIn().togetherWith(fadeOut()) }) { state ->
        if (!state.isReady) {
            CustomAnimatedPlaceHolder()
        } else {
            val rotation by infiniteTransition.animateFloat(
                initialValue = 360f,
                targetValue = 0f,
                animationSpec = infiniteRepeatable(
                    tween(50000, easing = LinearEasing),
                    RepeatMode.Restart
                )
            )
            val brush =
                Brush.horizontalGradient(listOf(secondary, state.rarity.getRarityColor()))

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
                        text = state.name.toString(),
                        style = magicItemTitle,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                TaperedRule(Modifier.padding(vertical = 8.dp), darkBlue)

                Text(
                    text = stringResource(state.rarity.stringRes()),
                    color = darkBlue,
                    style = MediumBoldSecondary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(state.rarity.getRarityColor())
                        .padding(4.dp)
                )

                Spacer(modifier = Modifier.height(4.dp))

                if (state.requireAttunement) {
                    Text(
                        text = stringResource(Res.string.magic_item_require_attunement),
                        color = secondary,
                        style = MediumBoldSecondary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(primaryDark)
                            .padding(4.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }

                PropertyLine(
                    Res.string.magic_item_category,
                    stringResource(state.category.stringRes())
                )

                Spacer(modifier = Modifier.height(4.dp))

                if (state.cost != null) {
                    PropertyLine(Res.string.magic_item_cost, state.cost.toString())
                    Spacer(modifier = Modifier.height(4.dp))
                }

                PropertyLine(Res.string.magic_item_weight, state.weight.toString())

                Spacer(modifier = Modifier.height(4.dp))

                LazyColumn(
                    Modifier.clip(RoundedCornerShape(8.dp))
                        .background(darkBlue)
                        .padding(8.dp)
                        .fillMaxWidth()
                        .weight(.6f)
                ) {
                    item {
                        Text(
                            text = state.description.toString(),
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
}