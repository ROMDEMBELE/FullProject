package di

import data.database.DriverFactory
import data.preference.SettingsStorage
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module
import ui.character.CharacterViewModel
import ui.character.edit.EditCharacterViewModel
import ui.monster.MonsterViewModel
import ui.campaign.main.CampaignMainViewModel
import ui.campaign.edit.EditCampaignViewModel
import ui.spell.SpellViewModel

actual fun platformModule(): Module = module {
    viewModelOf(::SpellViewModel)
    viewModelOf(::EditCharacterViewModel)
    viewModelOf(::MonsterViewModel)
    viewModelOf(::CharacterViewModel)
    viewModelOf(::EditCampaignViewModel)
    viewModelOf(::CampaignMainViewModel)

    single { DriverFactory(androidContext()) }
    single { SettingsStorage(androidContext()) }
}