import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import kotlinx.coroutines.launch
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.adventure
import org.dembeyo.shared.resources.ancient
import org.dembeyo.shared.resources.menu_screen_title
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.home.MenuScreen
import ui.home.MenuDrawer
import ui.darkBlue
import ui.darkGray
import ui.primary
import ui.secondary
import ui.settings.CampaignSettingsScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
        val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
        val scope = rememberCoroutineScope()
        Navigator(screen = MenuScreen()) { navigator ->
            Scaffold(
                scaffoldState = scaffoldState,
                drawerBackgroundColor = secondary,
                drawerContent = {
                    MenuDrawer(navigator) {
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                    }
                },
                topBar = {
                    TopAppBar(backgroundColor = darkBlue) {
                        Box(Modifier.fillMaxSize()) {
                            IconButton(
                                modifier = Modifier.align(Alignment.CenterStart),
                                onClick = {
                                    scope.launch {
                                        scaffoldState.drawerState.open()
                                    }
                                }) {
                                Icon(Icons.Filled.Menu, null, tint = primary)
                            }
                            Text(
                                stringResource(Res.string.menu_screen_title),
                                Modifier.fillMaxWidth().align(Alignment.Center),
                                fontSize = 30.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily(Font(Res.font.ancient)),
                                color = primary,
                            )
                            IconButton(
                                modifier = Modifier.align(Alignment.CenterEnd),
                                onClick = {
                                    scope.launch {
                                        navigator.push(CampaignSettingsScreen())
                                    }
                                }) {
                                Image(
                                    painter = painterResource(Res.drawable.adventure),
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(primary),
                                    modifier = Modifier.size(24.dp).aspectRatio(1f)
                                )
                            }
                        }

                    }
                }
            ) { _ ->
                Box(
                    Modifier.fillMaxSize()
                        .background(Brush.verticalGradient(listOf(secondary, secondary, darkGray)))
                ) {
                    CurrentScreen()
                }
            }
        }
    }
}