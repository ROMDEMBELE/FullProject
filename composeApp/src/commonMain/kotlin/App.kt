
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.menu_screen_title
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.composable.secondary
import ui.home.MenuDrawer
import ui.navigation.AppNavHost
import ui.navigation.AppRoute
import ui.navigation.AppTopBar

@Composable
@Preview
fun App() {
    val lang by remember { mutableStateOf(Language.French.isoFormat) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val currentScreen by navController.currentBackStackEntryFlow.collectAsState(null)

    val toggleDrawer: () -> Unit = {
        scope.launch {
            if (drawerState.isOpen) {
                drawerState.close()
            } else {
                drawerState.open()
            }
        }
    }

    LocalizedApp(lang) {
        Scaffold(
            topBar = {
                val currentRoute = AppRoute.getRoute(currentScreen?.destination?.route)
                val title = stringResource(currentRoute?.title ?: Res.string.menu_screen_title)
                AppTopBar(title, toggleDrawer)
            }
        ) { innerPadding ->
            Box(Modifier.padding(innerPadding).background(secondary)) {
                ModalNavigationDrawer(
                    modifier = Modifier.consumeWindowInsets(innerPadding),
                    drawerState = drawerState,
                    drawerContent = {
                            MenuDrawer(navController, toggleDrawer)
                    },
                    content = {
                        AppNavHost(navController)
                    }
                )
            }
        }
    }
}

