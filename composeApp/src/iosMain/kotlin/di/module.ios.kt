package di

import IosContext
import data.database.DriverFactory
import data.preference.SettingsStorage
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ui.campaign.edit.EditCampaignViewModel
import ui.campaign.main.CampaignMainViewModel
import ui.character.CharacterViewModel
import ui.character.edit.EditCharacterViewModel
import ui.monster.MonsterDetailsViewModel
import ui.monster.MonsterListViewModel
import ui.spell.SpellDetailsViewModel
import ui.spell.SpellListViewModel

actual fun platformModule(): Module = module {
    single {
        Darwin.create()
    }
    single { DriverFactory() }
    factoryOf(::SpellListViewModel)
    factoryOf(::SpellDetailsViewModel)
    factoryOf(::EditCharacterViewModel)
    factoryOf(::CharacterViewModel)
    factoryOf(::MonsterListViewModel)
    factoryOf(::MonsterDetailsViewModel)
    factoryOf(::EditCampaignViewModel)
    factoryOf(::CampaignMainViewModel)

    single { SettingsStorage(IosContext) }

}

object ViewModelProvider : KoinComponent {
    fun getSpellScreenListViewModel() = SpellListViewModel(get())
    fun getSpellScreenDetailsViewModel() = SpellDetailsViewModel(get())
    fun getEditCharacterViewModel() = EditCharacterViewModel(get(), get(), get(), get(), get())
    fun getCharacterViewModel() = CharacterViewModel(get())
    fun getMonsterListViewModel() = MonsterListViewModel(get())
    fun getMonsterDetailsViewModel() = MonsterDetailsViewModel(get())
}