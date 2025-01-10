package ui.home

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.ancient
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.composable.bounceClick
import ui.composable.darkBlue
import ui.composable.darkGray
import ui.composable.darkPrimary
import ui.composable.primary
import ui.composable.roundCornerShape
import ui.composable.secondary
import ui.navigation.AppRoute

@Composable
fun MenuScreen(navController: NavHostController) {

    val scope = rememberCoroutineScope()

    LazyVerticalGrid(
        modifier = Modifier.background(secondary).fillMaxSize().padding(16.dp),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        items(
            listOf(
                AppRoute.SEARCH_SPELL,
                AppRoute.SEARCH_MONSTER,
                AppRoute.SEARCH_FEAT,
                //AppRoute.SEARCH_CHARACTER,
                //AppRoute.SEARCH_EQUIPMENT
            )
        ) { route ->
            MenuItemView(route) {
                scope.launch {
                    delay(200)
                    navController.navigate(route.route)
                }
            }
        }
    }
}

internal class NoRippleInteractionSource : MutableInteractionSource {

    override val interactions: Flow<Interaction> = emptyFlow()

    override suspend fun emit(interaction: Interaction) {}

    override fun tryEmit(interaction: Interaction) = true
}

@Composable
fun MenuItemView(route: AppRoute, onClick: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition()
    val colorAnimation by infiniteTransition.animateColor(
        primary,
        darkPrimary,
        infiniteRepeatable(tween(10000), RepeatMode.Reverse)
    )
    val interactionSource = remember { NoRippleInteractionSource() }
    Button(
        modifier = Modifier.height(220.dp).bounceClick(),
        shape = roundCornerShape,
        interactionSource = interactionSource,
        border = BorderStroke(2.dp, secondary),
        elevation = ButtonDefaults.elevation(4.dp),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(darkBlue),
        onClick = onClick,
    ) {
        val surfaceColorGradient =
            Brush.linearGradient(listOf(darkBlue, darkBlue, darkGray, darkBlue, darkBlue))
        Surface(
            color = Color.Transparent,
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(2.dp, secondary),
            modifier = Modifier.padding(8.dp).clip(RoundedCornerShape(10.dp))
                .background(surfaceColorGradient)
        ) {
            Box(Modifier.fillMaxSize().padding(8.dp)) {
                Image(
                    painter = painterResource(route.icon),
                    contentDescription = route.icon.toString(),
                    modifier = Modifier.padding(16.dp).fillMaxSize()
                        .aspectRatio(1f)
                        .alpha(0.6f)
                        .drawWithContent {
                            drawContent()
                            drawRect(colorAnimation, blendMode = BlendMode.SrcAtop)
                        }
                        .align(Alignment.Center)
                )

                Text(
                    text = stringResource(route.title),
                    modifier = Modifier.align(Alignment.Center).fillMaxWidth(),
                    color = secondary,
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                    fontFamily = FontFamily(Font(Res.font.ancient)),
                    style = TextStyle(
                        shadow = Shadow(
                            color = primary,
                            offset = Offset(5f, 5f),
                            blurRadius = 12f
                        )
                    )
                )

            }
        }
    }
}

