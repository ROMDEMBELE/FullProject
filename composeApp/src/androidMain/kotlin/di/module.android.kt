package di

import data.database.room.EncounterDatabase
import data.database.sqlDelight.DriverFactory
import data.preference.SettingsStorage
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module
import ui.campaign.edit.EditCampaignViewModel
import ui.campaign.main.CampaignViewModel
import ui.character.CharacterViewModel
import ui.character.edit.EditCharacterViewModel
import ui.encounter.EncounterListViewModel
import ui.magicItem.details.MagicItemDetailsViewModel
import ui.magicItem.list.MagicItemListViewModel
import ui.monster.details.MonsterDetailsViewModel
import ui.monster.list.MonsterListViewModel
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
    viewModelOf(::CampaignViewModel)
    viewModelOf(::MagicItemListViewModel)
    viewModelOf(::MagicItemDetailsViewModel)
    viewModelOf(::EncounterListViewModel)

    single { DriverFactory(androidContext()) }
    single { SettingsStorage(androidContext()) }
}