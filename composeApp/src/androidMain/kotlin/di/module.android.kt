package di

import data.database.DriverFactory
import data.preference.SettingsStorage
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module
import ui.campaign.edit.EditCampaignViewModel
import ui.campaign.main.CampaignMainViewModel
import ui.character.CharacterViewModel
import ui.character.edit.EditCharacterViewModel
import ui.monster.MonsterDetailsViewModel
import ui.monster.MonsterListViewModel
import ui.spell.details.SpellDetailsViewModel
import ui.spell.list.SpellListViewModel

actual fun platformModule(): Module = module {
    viewModelOf(::SpellListViewModel)
    viewModelOf(::SpellDetailsViewModel)
    viewModelOf(::EditCharacterViewModel)
    viewModelOf(::MonsterListViewModel)
    viewModelOf(::MonsterDetailsViewModel)
    viewModelOf(::CharacterViewModel)
    viewModelOf(::EditCampaignViewModel)
    viewModelOf(::CampaignMainViewModel)

    single { DriverFactory(androidContext()) }
    single { SettingsStorage(androidContext()) }
}