package ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import ui.home.MenuScreen
import ui.monster.details.MonsterDetailScreen
import ui.monster.details.MonsterDetailsViewModel
import ui.monster.search.SearchMonsterScreen
import ui.monster.search.SearchMonsterViewModel
import ui.navigation.AppRoute.HOME
import ui.navigation.AppRoute.MONSTER
import ui.navigation.AppRoute.SEARCH_MONSTER
import ui.navigation.AppRoute.SEARCH_SPELL
import ui.navigation.AppRoute.SPELL
import ui.spell.details.SpellDetailsScreen
import ui.spell.details.SpellDetailsViewModel
import ui.spell.search.SearchSpellScreen
import ui.spell.search.SearchSpellViewModel

@Composable
fun AppNavHost(navController: NavHostController) {
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
            val viewModel: SpellDetailsViewModel = koinViewModel { parametersOf(index) }
            SpellDetailsScreen(viewModel)
        }
        composable(SEARCH_MONSTER.route) {
            val viewModel: SearchMonsterViewModel = koinViewModel()
            SearchMonsterScreen(navController, viewModel)
        }
        composable(
            route = MONSTER.route,
            arguments = listOf(index)
        ) {
            val index = it.arguments?.getString("index")
                ?: throw IllegalStateException("Nav argument 'index' is required to display a monster")
            val viewModel: MonsterDetailsViewModel = koinInject { parametersOf(index) }

            MonsterDetailScreen(navController, viewModel)
        }
    }
}