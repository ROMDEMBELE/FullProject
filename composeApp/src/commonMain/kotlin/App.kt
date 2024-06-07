
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import fullproject.composeapp.generated.resources.Res
import fullproject.composeapp.generated.resources.menu_screen_title
import fullproject.composeapp.generated.resources.ornament
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.MenuScreen
import ui.composable.MenuDrawer
import ui.darkBlue
import ui.darkGray
import ui.primary

@Composable
@Preview
fun App() {
    MaterialTheme {
        val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
        val scope = rememberCoroutineScope()
        Navigator(screen = MenuScreen()) { navigator ->
            Scaffold(
                scaffoldState = scaffoldState,
                drawerBackgroundColor = darkBlue,
                drawerContent = {
                    MenuDrawer(navigator) {
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                    }
                },
                topBar = {
                    TopAppBar(backgroundColor = darkBlue) {
                        IconButton(onClick = {
                            scope.launch {
                                scaffoldState.drawerState.open()
                            }
                        }) {
                            Icon(Icons.Filled.Menu, null, tint = Color.White)
                        }
                        Text(
                            stringResource(Res.string.menu_screen_title),
                            Modifier.weight(1f).padding(4.dp),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif,
                            color = Color.White,
                        )
                    }
                }
            ) { _ ->
                Box(Modifier.fillMaxSize().background(darkGray)) {
                    Image(
                        painterResource(Res.drawable.ornament),
                        null,
                        modifier = Modifier.padding(20.dp)
                            .fillMaxSize()
                            .aspectRatio(1f)
                            .alpha(0.6f)
                            .align(Alignment.Center),
                        colorFilter = ColorFilter.tint(primary)
                    )
                    CurrentScreen()
                }
            }
        }
    }
}