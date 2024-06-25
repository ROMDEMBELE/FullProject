package di

import data.database.DriverFactory
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ui.character.CharacterViewModel
import ui.character.edit.EditCharacterViewModel
import ui.monster.MonsterViewModel
import ui.spell.SpellViewModel

actual fun platformModule(): Module = module {
    single {
        Darwin.create()
    }
    single { DriverFactory() }
    factory { SpellViewModel(get()) }
    factoryOf(::EditCharacterViewModel)
    factory { CharacterViewModel(get()) }
    factory { MonsterViewModel(get()) }
}

object ViewModelProvider : KoinComponent {
    fun getSpellScreenViewModel() = SpellViewModel(get())
    fun getEditCharacterViewModel() = EditCharacterViewModel(get(), get(), get(), get(), get())
    fun getCharacterViewModel() = CharacterViewModel(get())
    fun getMonsterViewModel() = MonsterViewModel(get())
}