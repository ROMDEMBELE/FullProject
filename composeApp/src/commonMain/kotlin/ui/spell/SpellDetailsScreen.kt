package ui.spell

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import domain.Level
import domain.MagicSchool
import domain.Spell
import kotlinx.coroutines.launch
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.magic
import org.dembeyo.shared.resources.minus_circle
import org.dembeyo.shared.resources.plus_circle
import org.jetbrains.compose.resources.painterResource
import ui.clip
import ui.darkBlue
import ui.darkGray
import ui.darkPrimary
import ui.lightBlue
import ui.lightGray
import ui.mediumBoldWhite
import ui.primary
import ui.smallBoldWhite


class SpellDetailsScreen(private val spell: Spell) : Screen {

    override val key: ScreenKey
        get() = uniqueScreenKey

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val scope = rememberCoroutineScope()
        val pagerState = rememberPagerState(pageCount = { spell.damageSlot.size })
        Column {
            Box(Modifier.fillMaxWidth().background(darkGray).height(120.dp)) {
                if (spell.school != null) {
                    MagicSchoolClip(spell.school, Modifier.align(Alignment.TopStart))
                }

                Image(
                    painterResource(Res.drawable.magic),
                    null,
                    colorFilter = ColorFilter.tint(primary),
                    modifier = Modifier.align(Alignment.Center)
                        .height(100.dp)
                        .padding(12.dp)
                        .alpha(0.6f)
                )
                Text(
                    spell.name,
                    Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )

                LevelClip(spell.level, Modifier.align(Alignment.TopEnd))

                if (spell.ritual == true) {
                    Text(
                        text = "Ritual",
                        modifier = Modifier.align(Alignment.BottomEnd)
                            .padding(8.dp)
                            .width(120.dp)
                            .clip(CutCornerShape(8.dp))
                            .background(lightBlue),
                        style = clip,
                        color = Color.White
                    )
                }
                if (spell.concentration == true) {
                    Text(
                        text = "Concentration",
                        modifier = Modifier.align(Alignment.BottomStart)
                            .padding(8.dp)
                            .width(120.dp)
                            .clip(CutCornerShape(8.dp))
                            .background(darkPrimary),
                        style = clip,
                        color = Color.White
                    )
                }
            }

            Box(Modifier.fillMaxWidth().background(darkBlue)) {
                Row(
                    Modifier.padding(8.dp).fillMaxWidth().height(30.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(spell.range.toString(), style = smallBoldWhite)
                    Text(spell.duration.toString(), style = smallBoldWhite)
                    Text(spell.components.toString(), style = smallBoldWhite)
                    Text(spell.casting_time.toString(), style = smallBoldWhite)
                }
            }

            Surface(color = lightBlue, modifier = Modifier.weight(1f)) {
                LazyColumn {
                    item {
                        Text(
                            spell.text.toString(),
                            Modifier.padding(8.dp),
                            fontSize = 14.sp,
                            fontFamily = FontFamily.Serif,
                            color = Color.White
                        )
                    }
                }
            }

            if (spell.damageSlot.isNotEmpty()) {
                Box(Modifier.fillMaxWidth().background(darkBlue)) {
                    Row(
                        Modifier.padding(8.dp).fillMaxWidth().height(30.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
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
                                colorFilter = ColorFilter.tint(if (pagerState.canScrollBackward) Color.White else lightGray)
                            )
                        }
                        Text(
                            "Level " + spell.damageSlot.toList()[pagerState.currentPage].first.level,
                            style = mediumBoldWhite
                        )
                        IconButton(onClick = {
                            scope.launch {
                                pagerState.scrollToPage(pagerState.currentPage + 1)
                            }
                        }, enabled = pagerState.canScrollForward) {
                            Image(
                                painterResource(Res.drawable.plus_circle),
                                null,
                                colorFilter = ColorFilter.tint(if (pagerState.canScrollForward) Color.White else lightGray)
                            )
                        }
                    }
                }
                HorizontalPager(pagerState) { pageIndex ->
                    val damage = spell.damageSlot.toList()[pageIndex]
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.background(darkGray).fillMaxWidth().padding(16.dp),
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = damage.second,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace,
                                textAlign = TextAlign.Center,
                                fontSize = 30.sp
                            )
                            Spacer(Modifier.width(12.dp))
                            Text(
                                text = spell.damageType.toString(),
                                color = primary,
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
    fun MagicSchoolClip(magicSchool: MagicSchool, modifier: Modifier) {
        Text(
            magicSchool.displayName,
            modifier.padding(8.dp)
                .width(120.dp)
                .clip(CutCornerShape(8.dp))
                .background(color = magicSchool.color),
            style = clip,
            color = Color.Black
        )
    }

    @Composable
    fun LevelClip(level: Level, modifier: Modifier) {
        Text(
            "Level ${level.level}",
            modifier.padding(8.dp)
                .width(120.dp)
                .clip(CutCornerShape(8.dp))
                .background(color = level.color),
            style = clip,
            color = Color.Black
        )
    }

}