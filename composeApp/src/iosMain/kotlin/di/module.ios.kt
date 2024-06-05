package di

import io.ktor.client.engine.darwin.Darwin
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.module.Module
import org.koin.dsl.module
import ui.spell.SpellScreenViewModel

actual fun platformModule(): Module = module {
    single {
        Darwin.create()
    }
    factory { SpellScreenViewModel(get()) }
}

object ViewModelProvider : KoinComponent {
    fun getSpellScreenViewModel() = SpellScreenViewModel(get())
}