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
import ui.character.CharacterViewModel
import ui.character.edit.EditCharacterViewModel
import ui.monster.MonsterViewModel
import ui.campaign.main.CampaignMainViewModel
import ui.campaign.edit.EditCampaignViewModel
import ui.spell.SpellViewModel

actual fun platformModule(): Module = module {
    single {
        Darwin.create()
    }
    single { DriverFactory() }
    factory { SpellViewModel(get()) }
    factoryOf(::EditCharacterViewModel)
    factoryOf(::CharacterViewModel)
    factoryOf(::MonsterViewModel)
    factoryOf(::EditCampaignViewModel)
    factoryOf(::CampaignMainViewModel)

    single { SettingsStorage(IosContext) }

}

object ViewModelProvider : KoinComponent {
    fun getSpellScreenViewModel() = SpellViewModel(get())
    fun getEditCharacterViewModel() = EditCharacterViewModel(get(), get(), get(), get(), get())
    fun getCharacterViewModel() = CharacterViewModel(get())
    fun getMonsterViewModel() = MonsterViewModel(get())
}