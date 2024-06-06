package di

import data.sql_database.DriverFactory
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.module.Module
import org.koin.dsl.module
import ui.character.CharacterViewModel
import ui.spell.SpellScreenViewModel

actual fun platformModule(): Module = module {
    single {
        Darwin.create()
    }
    single { DriverFactory() }
    factory { SpellScreenViewModel(get()) }
    factory { CharacterViewModel(get())}
}

object ViewModelProvider : KoinComponent {
    fun getSpellScreenViewModel() = SpellScreenViewModel(get())
    fun getCharacterViewModel() = CharacterViewModel(get())
}