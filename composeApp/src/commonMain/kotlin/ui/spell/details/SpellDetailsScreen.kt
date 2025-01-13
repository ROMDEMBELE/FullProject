package ui.spell.details

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.concentration
import org.dembeyo.shared.resources.minus_circle
import org.dembeyo.shared.resources.ornament
import org.dembeyo.shared.resources.plus_circle
import org.dembeyo.shared.resources.ritual
import org.dembeyo.shared.resources.spell_casting_time
import org.dembeyo.shared.resources.spell_components
import org.dembeyo.shared.resources.spell_duration
import org.dembeyo.shared.resources.spell_materials
import org.dembeyo.shared.resources.spell_range
import org.dembeyo.shared.resources.spell_saving_throw
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.color
import ui.composable.CustomAnimatedPlaceHolder
import ui.composable.TaperedRule
import ui.composable.darkBlue
import ui.composable.lightBlue
import ui.composable.lightGray
import ui.composable.monsterTitle
import ui.composable.primary
import ui.composable.secondary
import ui.spell.details.composable.PropertyLine
import ui.spell.details.composable.SpellOptionItem
import ui.stringRes


@Composable
fun SpellDetailsScreen(viewModel: SpellDetailsViewModel) {
    val scope = rememberCoroutineScope()
    val infiniteTransition = rememberInfiniteTransition()
    val uiState by viewModel.uiState.collectAsState()

    val rotation by infiniteTransition.animateFloat(
        0f, 360f, infiniteRepeatable(tween(50000, easing = LinearEasing), RepeatMode.Restart)
    )

    AnimatedContent(uiState, transitionSpec = { fadeIn().togetherWith(fadeOut()) }) { state ->
        if (!state.isReady) {
            CustomAnimatedPlaceHolder()
        } else {
            val pagerState = rememberPagerState(pageCount = { state.castingOptions.size })
            val brush =
                Brush.horizontalGradient(listOf(state.school.color(), state.level.color()))
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

                    TextClip(
                        stringResource(state.school.stringRes()),
                        state.school.color(),
                        Alignment.TopStart
                    )

                    TextClip(" Level ${state.level.level}", state.level.color(), Alignment.TopEnd)

                    if (state.isRitual) {
                        TextClip(
                            text = stringResource(Res.string.ritual),
                            lightBlue,
                            Alignment.BottomEnd
                        )
                    }

                    if (state.isConcentration) {
                        TextClip(
                            text = stringResource(Res.string.concentration),
                            primary,
                            Alignment.BottomStart
                        )
                    }

                    Text(
                        state.name.toString(),
                        Modifier.align(Alignment.Center),
                        style = monsterTitle.copy(
                            color = darkBlue, shadow = Shadow(
                                color = state.school.color(),
                                offset = Offset(5f, 5f),
                                blurRadius = 12f
                            )
                        )
                    )
                }

                TaperedRule(Modifier.padding(vertical = 8.dp), darkBlue)

                PropertyLine(Res.string.spell_range, state.range.toString())

                PropertyLine(Res.string.spell_duration, state.duration.toString())

                val component = buildString {
                    if (state.verbal) append(" V")
                    if (state.somatic) append(" S")
                    if (state.material) append(" M")
                }

                PropertyLine(Res.string.spell_components, component)

                PropertyLine(Res.string.spell_casting_time, state.castingTime.toString())

                if (state.material && state.cost.isNullOrBlank().not()) {
                    PropertyLine(Res.string.spell_materials, state.cost.toString())
                }

                TaperedRule(Modifier.padding(vertical = 8.dp), darkBlue)

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
                    if (state.higherLevelDescription.isNullOrBlank().not()) {
                        item {
                            Text(
                                text = state.higherLevelDescription.toString(),
                                fontSize = 14.sp,
                                style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                                fontFamily = FontFamily.Serif,
                                color = secondary
                            )
                        }
                    }
                }


                if (state.savingThrowAbility != null) {
                    TaperedRule(Modifier.padding(vertical = 8.dp), darkBlue)

                    Text(
                        modifier = Modifier.fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(primary)
                            .padding(4.dp),
                        text = stringResource(
                            Res.string.spell_saving_throw,
                            stringResource(state.savingThrowAbility.stringRes())
                        ),
                        color = darkBlue,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp
                    )
                }

                if (state.castingOptions.isNotEmpty()) {
                    TaperedRule(Modifier.padding(vertical = 8.dp), darkBlue)

                    val animatedColorMinus by animateColorAsState(if (pagerState.canScrollBackward) secondary else lightGray)
                    val animatedColorPlus by animateColorAsState(if (pagerState.canScrollForward) darkBlue else lightGray)

                    Box(Modifier.fillMaxWidth()) {
                        HorizontalPager(state = pagerState) { pageIndex ->
                            state.castingOptions.toList()[pageIndex].let {
                                SpellOptionItem(
                                    level = it.level,
                                    dice = it.damageRoll,
                                    duration = it.duration,
                                    targetCount = it.targetCount,
                                    type = state.damageType
                                )
                            }
                        }

                        IconButton(
                            onClick = { scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) } },
                            enabled = pagerState.canScrollBackward,
                            modifier = Modifier.align(Alignment.CenterStart).padding(16.dp)
                                .size(25.dp)
                        ) {
                            Image(
                                painterResource(Res.drawable.minus_circle),
                                null,
                                colorFilter = ColorFilter.tint(animatedColorMinus)
                            )
                        }

                        IconButton(
                            onClick = { scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) } },
                            enabled = pagerState.canScrollForward,
                            modifier = Modifier.align(Alignment.CenterEnd).padding(16.dp)
                                .size(25.dp)

                        ) {
                            Image(
                                painterResource(Res.drawable.plus_circle),
                                null,
                                colorFilter = ColorFilter.tint(animatedColorPlus)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BoxScope.TextClip(text: String, color: Color, alignment: Alignment) {
    Text(
        text = text,
        modifier = Modifier
            .width(140.dp)
            .border(2.dp, darkBlue, CircleShape)
            .clip(CircleShape)
            .background(color = color)
            .padding(2.dp)
            .align(alignment),
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        fontSize = 12.sp
    )
}