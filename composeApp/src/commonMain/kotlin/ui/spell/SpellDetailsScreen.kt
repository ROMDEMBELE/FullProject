package ui.spell

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import domain.model.DamageType
import domain.model.Level
import domain.model.spell.Spell
import kotlinx.coroutines.launch
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.magic
import org.dembeyo.shared.resources.minus_circle
import org.dembeyo.shared.resources.ornament
import org.dembeyo.shared.resources.plus_circle
import org.dembeyo.shared.resources.spell_area_of_effect
import org.dembeyo.shared.resources.spell_casting_time
import org.dembeyo.shared.resources.spell_components
import org.dembeyo.shared.resources.spell_duration
import org.dembeyo.shared.resources.spell_materials
import org.dembeyo.shared.resources.spell_range
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import ui.composable.MediumBold
import ui.composable.TaperedRule
import ui.composable.darkBlue
import ui.composable.darkPrimary
import ui.composable.generateIcon
import ui.composable.lightBlue
import ui.composable.lightGray
import ui.composable.monsterTitle
import ui.composable.primary
import ui.composable.propertyText
import ui.composable.propertyTitle
import ui.composable.secondary


class SpellDetailsScreen(private val index: String) : Screen {

    override val key: ScreenKey
        get() = uniqueScreenKey

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val viewModel: SpellDetailsViewModel = koinInject()
        var uiState by remember { mutableStateOf<Spell?>(null) }

        val infiniteTransition = rememberInfiniteTransition()
        val scale by infiniteTransition.animateFloat(
            0.8f, 1f, infiniteRepeatable(
                animation = tween(1000),
                repeatMode = RepeatMode.Reverse
            )
        )

        val rotation by infiniteTransition.animateFloat(
            0f, 360f, infiniteRepeatable(tween(50000, easing = LinearEasing), RepeatMode.Restart)
        )

        LaunchedEffect(index) {
            uiState = viewModel.getSpellDetailsByIndex(index)
        }

        AnimatedContent(uiState) { spell ->
            val details = uiState?.details
            if (spell != null && details != null) {
                val scope = rememberCoroutineScope()
                val pagerState = rememberPagerState(pageCount = { details.damageByLevel.size })
                val brush =
                    Brush.horizontalGradient(listOf(details.school.color, spell.level.color))
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
                            details.school.displayName,
                            details.school.color,
                            Alignment.TopStart
                        )

                        TextClip(" Level ${spell.level.level}", spell.level.color, Alignment.TopEnd)

                        if (details.ritual) {
                            TextClip(
                                text = "Ritual",
                                lightBlue,
                                Alignment.BottomEnd
                            )
                        }
                        if (details.concentration) {
                            TextClip(
                                text = "Concentration",
                                primary,
                                Alignment.BottomStart
                            )
                        }

                        Text(
                            spell.name,
                            Modifier.align(Alignment.Center),
                            style = monsterTitle.copy(
                                color = darkBlue, shadow = Shadow(
                                    color = details.school.color,
                                    offset = Offset(5f, 5f),
                                    blurRadius = 12f
                                )
                            )
                        )
                    }

                    TaperedRule(Modifier.padding(vertical = 8.dp), darkBlue)

                    PropertyLine(Res.string.spell_range, details.range)

                    PropertyLine(Res.string.spell_duration, details.duration)

                    PropertyLine(Res.string.spell_components, details.components)

                    PropertyLine(Res.string.spell_casting_time, details.castingTime)

                    if (details.material != null) {
                        PropertyLine(Res.string.spell_materials, details.material)
                    }

                    if (details.areaOfEffect != null) {
                        PropertyLine(Res.string.spell_area_of_effect, details.areaOfEffect)
                    }

                    TaperedRule(Modifier.padding(vertical = 8.dp), darkBlue)

                    LazyColumn(
                        Modifier.clip(RoundedCornerShape(8.dp))
                            .background(darkBlue)
                            .padding(8.dp)
                            .weight(.6f)
                    ) {
                        items(details.description) { text ->
                            Text(
                                text,
                                fontSize = 14.sp,
                                style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                                fontFamily = FontFamily.Serif,
                                color = secondary
                            )
                        }
                    }


                    if (details.savingThrow != null) {
                        TaperedRule(Modifier.padding(vertical = 8.dp), darkBlue)

                        Text(
                            modifier = Modifier.fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(primary)
                                .padding(4.dp),
                            text = "${details.savingThrow} of...",
                            color = darkBlue,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace,
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp
                        )
                    }

                    if (details.damageByLevel.isNotEmpty()) {
                        TaperedRule(Modifier.padding(vertical = 8.dp), darkBlue)

                        val animatedColorMinus by animateColorAsState(if (pagerState.canScrollBackward) secondary else lightGray)
                        val animatedColorPlus by animateColorAsState(if (pagerState.canScrollForward) darkBlue else lightGray)

                        Box(Modifier.fillMaxWidth()) {
                            HorizontalPager(state = pagerState) { pageIndex ->
                                val (level, damage) = details.damageByLevel.toList()[pageIndex]
                                DamageItem(level, damage.dice, damage.type)
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
            } else {
                Box(Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(Res.drawable.magic),
                        colorFilter = ColorFilter.tint(darkPrimary),
                        contentDescription = "monster",
                        modifier = Modifier.align(Alignment.Center)
                            .alpha(scale)
                            .size(200.dp)
                            .graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                                transformOrigin = TransformOrigin.Center
                            }
                    )
                }
            }
        }
    }

    @Composable
    fun DamageItem(level: Level, dice: String, type: DamageType?) {
        val damageBrush = Brush.linearGradient(listOf(darkBlue, darkBlue, level.color))
        Box(Modifier.height(70.dp).fillMaxWidth().clip(RoundedCornerShape(8.dp)).background(damageBrush).padding(4.dp)) {
            Text(
                text = "Lv${level.level}",
                style = MediumBold.copy(color = secondary, fontSize = 10.sp),
                modifier = Modifier.align(Alignment.TopCenter)
            )
            type?.generateIcon(dice, Modifier.align(Alignment.Center))
        }
    }

    @Composable
    fun PropertyLine(title: StringResource, value: String) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 2.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(darkBlue),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(title),
                style = propertyTitle.copy(color = secondary),
                modifier = Modifier.padding(4.dp)
            )
            Text(
                text = value.capitalize(Locale.current),
                modifier = Modifier.padding(4.dp),
                textAlign = TextAlign.End,
                style = propertyText.copy(color = secondary),
            )
        }
    }

    @Composable
    fun BoxScope.TextClip(text: String, color: Color, alignment: Alignment) {
        Text(
            text = text,
            modifier = Modifier
                .width(140.dp)
                .padding(8.dp)
                .border(2.dp, darkBlue, CircleShape)
                .clip(CircleShape)
                .background(color = color)
                .padding(4.dp)
                .align(alignment),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 14.sp
        )
    }

}