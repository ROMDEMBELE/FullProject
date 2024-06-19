package di

import data.database.DriverFactory
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module
import ui.player.PlayerViewModel
import ui.monster.MonsterViewModel
import ui.spell.SpellViewModel

actual fun platformModule(): Module = module {
    viewModelOf(::SpellViewModel)
    viewModelOf(::PlayerViewModel)
    viewModelOf(::MonsterViewModel)
    single { DriverFactory(androidContext()) }
}