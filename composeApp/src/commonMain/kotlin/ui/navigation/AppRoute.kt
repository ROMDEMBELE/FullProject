package ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.battle
import org.dembeyo.shared.resources.home
import org.dembeyo.shared.resources.knight
import org.dembeyo.shared.resources.magic
import org.dembeyo.shared.resources.magic_item
import org.dembeyo.shared.resources.menu_battle
import org.dembeyo.shared.resources.menu_feat
import org.dembeyo.shared.resources.menu_home
import org.dembeyo.shared.resources.menu_magic_item
import org.dembeyo.shared.resources.menu_monster
import org.dembeyo.shared.resources.menu_spell
import org.dembeyo.shared.resources.monster
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

enum class AppRoute(val route: String, val title: StringResource, val icon: DrawableResource) {
    HOME("home", Res.string.menu_home, Res.drawable.home),
    BATTLE("battle", Res.string.menu_battle, Res.drawable.battle),
    SEARCH_SPELL("search_spell", Res.string.menu_spell, Res.drawable.magic),
    SPELL("spell/{index}", Res.string.menu_spell, Res.drawable.magic),
    SEARCH_MONSTER("search_monster", Res.string.menu_monster, Res.drawable.monster),
    MONSTER("monster/{index}", Res.string.menu_monster, Res.drawable.monster),
    SEARCH_MAGIC_ITEM("search_magic_item", Res.string.menu_magic_item, Res.drawable.magic_item),
    MAGIC_ITEM("magic_item/{index}", Res.string.menu_magic_item, Res.drawable.magic_item),

    //SEARCH_CHARACTER("search_character", Res.string.menu_character, Res.drawable.knight),
    SEARCH_FEAT("feats", Res.string.menu_feat, Res.drawable.knight);
    //SEARCH_EQUIPMENT("search_equipment", Res.string.menu_equipment, Res.drawable.sword_tie),
    //EQUIPMENT("equipment/{index}", Res.string.menu_equipment, Res.drawable.sword_tie);
    //SETTINGS("settings", Res.string.menu_settings, Res.drawable.settings),
    //PROFILE("profile", Res.string.menu_profile, Res.drawable.profile),
    //ABOUT("about", Res.string.menu_about, Res.drawable.info)

    private fun isRouteMatching(route: String): Boolean {
        val regexPattern =
            this.route.replace("{index}", ".*").toRegex() // Remplace {index} par un wildcard
        return regexPattern.matches(route)
    }

    companion object {
        fun getRoute(route: String?): AppRoute? {
            return entries.firstOrNull { it.isRouteMatching(route.toString()) }
        }
    }
}

val index: NamedNavArgument = navArgument("index") {
    type = NavType.StringType
    nullable = false
}