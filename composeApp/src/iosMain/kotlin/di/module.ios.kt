package di

import data.database.DriverFactory
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.module.Module
import org.koin.dsl.module
import ui.monster.MonsterViewModel
import ui.player.CharacterViewModel
import ui.player.edit.EditCharacterViewModel
import ui.spell.SpellViewModel

actual fun platformModule(): Module = module {
    single {
        Darwin.create()
    }
    single { DriverFactory() }
    factory { SpellViewModel(get()) }
    factory { EditCharacterViewModel(get(), get()) }
    factory { CharacterViewModel(get()) }
    factory { MonsterViewModel(get()) }
}

object ViewModelProvider : KoinComponent {
    fun getSpellScreenViewModel() = SpellViewModel(get())
    fun getEditCharacterViewModel() = EditCharacterViewModel(get(), get())
    fun getCharacterViewModel() = CharacterViewModel(get())
    fun getMonsterViewModel() = MonsterViewModel(get())
}