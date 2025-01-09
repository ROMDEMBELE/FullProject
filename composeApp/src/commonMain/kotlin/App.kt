import AppRoute.HOME
import AppRoute.MONSTER
import AppRoute.SEARCH_MONSTER
import AppRoute.SEARCH_SPELL
import AppRoute.SPELL
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
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
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf
import ui.composable.darkBlue
import ui.composable.primary
import ui.composable.secondary
import ui.home.MenuDrawer
import ui.home.MenuScreen
import ui.monster.details.MonsterDetailScreen
import ui.monster.details.MonsterDetailsViewModel
import ui.monster.search.SearchMonsterScreen
import ui.monster.search.SearchMonsterViewModel
import ui.spell.details.SpellDetailsScreen
import ui.spell.details.SpellDetailsViewModel
import ui.spell.search.SearchSpellScreen
import ui.spell.search.SearchSpellViewModel

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
            }
        ) { _ ->
            Box {
                NavHost(
                    navController = navController,
                    startDestination = HOME.route,
                ) {
                    composable(HOME.route) {
                        MenuScreen(navController)
                    }
                    composable(SEARCH_SPELL.route) {
                        val viewModel: SearchSpellViewModel = koinInject()
                        SearchSpellScreen(navController, viewModel)
                    }
                    composable(
                        route = SPELL.route,
                        arguments = listOf(index)
                    ) {
                        val index = it.arguments?.getString("index")
                            ?: throw IllegalStateException("Nav argument 'index' is required to display a spell")
                        val viewModel: SpellDetailsViewModel = koinInject { parametersOf(index) }
                        SpellDetailsScreen(viewModel)
                    }
                    composable(SEARCH_MONSTER.route) {

                        val viewModel: SearchMonsterViewModel = koinInject()
                        SearchMonsterScreen(navController, viewModel)
                    }
                    composable(
                        route = MONSTER.route,
                        arguments = listOf(index)
                    ) {
                        val index = it.arguments?.getString("index")
                            ?: throw IllegalStateException("Nav argument 'index' is required to display a monster")
                        val viewModel: MonsterDetailsViewModel = koinInject()

                        MonsterDetailScreen(index, viewModel)
                    }
                }
            }
        }
    }
}

val index: NamedNavArgument = navArgument("index") {
    type = NavType.StringType
    nullable = false
}

enum class MenuItem