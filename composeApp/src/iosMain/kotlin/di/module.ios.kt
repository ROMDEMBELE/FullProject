package di

import data.database.DriverFactory
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.module.Module
import org.koin.dsl.module
import ui.player.PlayerViewModel
import ui.monster.MonsterViewModel
import ui.spell.SpellViewModel

actual fun platformModule(): Module = module {
    single {
        Darwin.create()
    }
    single { DriverFactory() }
    factory { SpellViewModel(get()) }
    factory { PlayerViewModel(get()) }
    factory { MonsterViewModel(get()) }
}

object ViewModelProvider : KoinComponent {
    fun getSpellScreenViewModel() = SpellViewModel(get())
    fun getCharacterViewModel() = PlayerViewModel(get())
    fun getMonsterViewModel() = MonsterViewModel(get())
}