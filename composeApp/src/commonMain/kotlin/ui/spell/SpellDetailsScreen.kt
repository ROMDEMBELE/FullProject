package ui.spell

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import domain.Spell
import kotlinx.coroutines.launch
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.magic
import org.dembeyo.shared.resources.minus_circle
import org.dembeyo.shared.resources.plus_circle
import org.jetbrains.compose.resources.painterResource
import ui.MediumBold
import ui.clip
import ui.darkBlue
import ui.darkPrimary
import ui.lightBlue
import ui.lightGray
import ui.primary
import ui.secondary
import ui.spellDetailsText


class SpellDetailsScreen(private val spell: Spell) : Screen {

    override val key: ScreenKey
        get() = uniqueScreenKey

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val scope = rememberCoroutineScope()
        val pagerState = rememberPagerState(pageCount = { spell.damageSlot.size })
        Column {
            Box(
                Modifier.fillMaxWidth()
                    .background(secondary)
                    .height(150.dp)
            ) {
                if (spell.school != null) {
                    TextClip(spell.school.displayName, spell.school.color, Alignment.TopStart)
                }

                val gradient = Brush.linearGradient(listOf(primary, darkPrimary))

                Image(
                    painterResource(Res.drawable.magic),
                    null,
                    modifier = Modifier.align(Alignment.Center)
                        .padding(12.dp)
                        .alpha(0.2f)
                        .drawWithContent {
                            drawContent()
                            drawRect(gradient, blendMode = BlendMode.SrcAtop)
                        }
                )

                TextClip(" Level ${spell.level.level}", spell.level.color, Alignment.TopEnd)

                if (spell.ritual == true) {
                    TextClip(
                        text = "Ritual",
                        lightBlue,
                        Alignment.BottomEnd
                    )
                }
                if (spell.concentration == true) {
                    TextClip(
                        text = "Concentration",
                        primary,
                        Alignment.BottomStart
                    )
                }

                Text(
                    spell.name,
                    Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center,
                    color = darkBlue,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
            }

            Box(Modifier.fillMaxWidth().background(darkBlue)) {
                Row(
                    Modifier.fillMaxWidth().height(60.dp).padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(spell.range.toString(), style = spellDetailsText)
                    Text(spell.duration.toString(), style = spellDetailsText)
                    Text(spell.components.toString(), style = spellDetailsText)
                    Text(spell.casting_time.toString(), style = spellDetailsText)
                }
            }

            Surface(color = secondary, modifier = Modifier.weight(1f)) {
                LazyColumn {
                    item {
                        Text(
                            spell.text.toString(),
                            Modifier.padding(8.dp),
                            fontSize = 16.sp,
                            style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                            fontFamily = FontFamily.Serif,
                            color = darkBlue
                        )
                    }
                }
            }

            val animatedColorMinus by animateColorAsState(if (pagerState.canScrollBackward) secondary else lightGray)
            val animatedColorPlus by animateColorAsState(if (pagerState.canScrollForward) secondary else lightGray)
            if (spell.damageSlot.isNotEmpty()) {
                Box(Modifier.fillMaxWidth().background(darkBlue)) {
                    Row(
                        Modifier.fillMaxWidth().height(60.dp).padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            scope.launch {
                                pagerState.scrollToPage(pagerState.currentPage - 1)
                            }
                        }, enabled = pagerState.canScrollBackward) {
                            Image(
                                painterResource(Res.drawable.minus_circle),
                                null,
                                colorFilter = ColorFilter.tint(animatedColorMinus)
                            )
                        }
                        Text(
                            "Level " + spell.damageSlot.toList()[pagerState.currentPage].first.level,
                            style = MediumBold.copy(color = secondary)
                        )
                        IconButton(onClick = {
                            scope.launch {
                                pagerState.scrollToPage(pagerState.currentPage + 1)
                            }
                        }, enabled = pagerState.canScrollForward) {
                            Image(
                                painterResource(Res.drawable.plus_circle),
                                null,
                                colorFilter = ColorFilter.tint(animatedColorPlus)
                            )
                        }
                    }
                }
                HorizontalPager(pagerState) { pageIndex ->
                    val damage = spell.damageSlot.toList()[pageIndex]
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.background(damage.first.color).fillMaxWidth()
                            .padding(16.dp),
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = damage.second,
                                color = darkBlue,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace,
                                textAlign = TextAlign.Center,
                                fontSize = 30.sp
                            )
                            Spacer(Modifier.width(12.dp))
                            Text(
                                text = spell.damageType.toString(),
                                color = darkPrimary,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace,
                                textAlign = TextAlign.Center,
                                fontSize = 30.sp
                            )
                        }
                    }
                }
            }
            if (spell.save != null) {
                Surface(color = primary) {
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        text = "Save DC ${spell.save}",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }

    @Composable
    fun BoxScope.TextClip(text: String, color: Color, alignment: Alignment) {
        Text(
            text,
            modifier = Modifier
                .width(120.dp)
                .padding(8.dp)
                .border(2.dp, darkBlue, CircleShape)
                .clip(CircleShape)
                .align(alignment)
                .background(color = color),
            style = clip
        )
    }

}