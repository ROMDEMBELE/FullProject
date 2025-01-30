package ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import ui.campaign.save.SaveCampaignScreen
import ui.campaign.save.SaveCampaignViewModel
import ui.campaign.search.CampaignListScreen
import ui.campaign.search.CampaignListViewModel
import ui.character.details.CharacterDetailsScreen
import ui.character.details.CharacterDetailsViewModel
import ui.character.save.SaveCharacterViewModel
import ui.character.search.SearchCharacterScreen
import ui.character.search.SearchCharacterViewModel
import ui.feat.search.SearchFeatScreen
import ui.feat.search.SearchFeatViewModel
import ui.home.MenuScreen
import ui.magicItem.details.MagicItemDetailsScreen
import ui.magicItem.details.MagicItemDetailsViewModel
import ui.magicItem.search.SearchMagicItemScreen
import ui.magicItem.search.SearchMagicItemViewModel
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
            val viewModel: SearchSpellViewModel = koinViewModel()
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
            val viewModel: MonsterDetailsViewModel = koinViewModel { parametersOf(index) }

            MonsterDetailScreen(viewModel)
        }
        composable(
            route = AppRoute.SEARCH_FEAT.route,
        ) {
            val viewModel: SearchFeatViewModel = koinViewModel()
            SearchFeatScreen(viewModel)
        }

        composable(
            route = AppRoute.SEARCH_MAGIC_ITEM.route,
        ) {
            val viewModel: SearchMagicItemViewModel = koinViewModel()
            SearchMagicItemScreen(navController, viewModel)
        }

        composable(
            route = AppRoute.MAGIC_ITEM.route,
            arguments = listOf(index)
        ) {
            val index = it.arguments?.getString("index")
                ?: throw IllegalStateException("Nav argument 'index' is required to display a magic item")
            val viewModel: MagicItemDetailsViewModel = koinViewModel { parametersOf(index) }
            MagicItemDetailsScreen(viewModel)
        }

        composable(
            route = AppRoute.SEARCH_CHARACTER.route,
        ) {
            val viewModel: SearchCharacterViewModel = koinViewModel()
            SearchCharacterScreen(navController, viewModel)
        }

        composable(
            route = AppRoute.CREATE_CHARACTER.route
        ) {
            val viewModel: SaveCharacterViewModel = koinViewModel()
            SearchCharacterScreen(navController, viewModel)
        }

        composable(
            route = AppRoute.CHARACTER.route,
            arguments = listOf(index)
        ) {
            val index = it.arguments?.getString("index")
                ?: throw IllegalStateException("Nav argument 'index' is required to display a character")
            val viewModel: CharacterDetailsViewModel = koinViewModel { parametersOf(index) }
            CharacterDetailsScreen(navController, viewModel)
        }

        composable(
            route = AppRoute.SEARCH_CAMPAIGN.route,
        ) {
            val viewModel: CampaignListViewModel = koinViewModel()
            CampaignListScreen(navController, viewModel)
        }

        composable(
            route = AppRoute.CREATE_CAMPAIGN.route,
        ) {
            val viewModel: SaveCampaignViewModel = koinViewModel()
            SaveCampaignScreen(navController, viewModel)
        }

        composable(
            route = AppRoute.EDIT_CAMPAIGN.route,
            arguments = listOf(index)
        ) {
            val index = it.arguments?.getString("index")
                ?: throw IllegalStateException("Nav argument 'index' is required to display a campaign")
            val viewModel: SaveCampaignViewModel = koinViewModel()
            SaveCampaignScreen(navController, viewModel, index)
        }
    }
}