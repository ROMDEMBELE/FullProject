package di

import data.database.DriverFactory
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module
import ui.character.CharacterViewModel
import ui.monster.MonsterScreenViewModel
import ui.spell.SpellScreenViewModel

actual fun platformModule(): Module = module {
    viewModelOf(::SpellScreenViewModel)
    viewModelOf(::CharacterViewModel)
    viewModelOf(::MonsterScreenViewModel)
    single { DriverFactory(androidContext()) }
}