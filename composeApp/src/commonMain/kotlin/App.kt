import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.DrawerValue
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.adventure
import org.dembeyo.shared.resources.ancient
import org.dembeyo.shared.resources.castle_empty
import org.dembeyo.shared.resources.menu_screen_title
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.composable.darkBlue
import ui.composable.primary
import ui.composable.secondary
import ui.home.MenuDrawer
import ui.navigation.AppNavHost
import ui.navigation.AppRoute

@Composable
@Preview
fun App() {
    val lang by remember { mutableStateOf(Language.French.isoFormat) }
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val currentScreen by navController.currentBackStackEntryFlow.collectAsState(null)

    val openDrawer: () -> Unit = {
        scope.launch {
            scaffoldState.drawerState.open()
        }
    }

    val closeDrawer: () -> Unit = {
        scope.launch {
            scaffoldState.drawerState.close()
        }
    }

    LocalizedApp(lang) {
        Scaffold(
            scaffoldState = scaffoldState,
            drawerBackgroundColor = secondary,
            drawerContent = {
                MenuDrawer(navController, closeDrawer)
            },
            topBar = {
                TopAppBar(backgroundColor = darkBlue) {
                    Box(Modifier.fillMaxSize()) {
                        IconButton(
                            modifier = Modifier.align(Alignment.CenterStart),
                            onClick = openDrawer
                        ) {
                            Image(
                                painter = painterResource(Res.drawable.adventure),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(primary),
                                modifier = Modifier.size(24.dp).aspectRatio(1f)
                            )
                        }
                        AnimatedContent(
                            currentScreen,
                            Modifier.fillMaxWidth().align(Alignment.Center)
                        ) { currentScreen ->
                            val title =
                                currentScreen?.destination?.route?.let {
                                    AppRoute.entries.find { route ->
                                        route.isRouteMatching(
                                            it
                                        )
                                    }?.title
                                } ?: Res.string.menu_screen_title
                            Text(
                                stringResource(title),
                                fontSize = 30.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily(Font(Res.font.ancient)),
                                color = primary,
                            )
                        }
                        IconButton(
                            enabled = true,
                            modifier = Modifier.align(Alignment.CenterEnd),
                            onClick = {
                                // TODO navigate to Campaign Screen
                            }) {
                            Image(
                                painter = painterResource(Res.drawable.castle_empty),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(primary),
                                modifier = Modifier.size(24.dp).aspectRatio(1f)
                            )
                        }
                    }
                }
            }
        ) { _ ->
            Box(Modifier.background(secondary)) {
                AppNavHost(navController)
            }
        }
    }
}

