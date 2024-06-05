package di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module
import ui.spell.SpellScreenViewModel

actual fun platformModule(): Module = module {
    viewModelOf(::SpellScreenViewModel)
}