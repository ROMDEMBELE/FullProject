package di

import data.database.sqlDelight.DriverFactory
import data.preference.PreferenceStorage
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module
import ui.campaign.edit.EditCampaignViewModel
import ui.campaign.main.CampaignViewModel
import ui.character.CharacterViewModel
import ui.character.edit.EditCharacterViewModel
import ui.encounter.EncounterListViewModel
import ui.feat.search.SearchFeatViewModel
import ui.magicItem.details.MagicItemDetailsViewModel
import ui.magicItem.search.SearchMagicItemViewModel
import ui.monster.details.MonsterDetailsViewModel
import ui.monster.search.SearchMonsterViewModel
import ui.spell.details.SpellDetailsViewModel
import ui.spell.search.SearchSpellViewModel

actual fun platformModule(): Module = module {
    viewModelOf(::SearchSpellViewModel)
    viewModelOf(::SpellDetailsViewModel)
    viewModelOf(::EditCharacterViewModel)
    viewModelOf(::SearchMonsterViewModel)
    viewModelOf(::MonsterDetailsViewModel)
    viewModelOf(::SearchFeatViewModel)
    viewModelOf(::CharacterViewModel)
    viewModelOf(::EditCampaignViewModel)
    viewModelOf(::CampaignViewModel)

    viewModelOf(::SearchMagicItemViewModel)
    viewModelOf(::MagicItemDetailsViewModel)
    viewModelOf(::EncounterListViewModel)

    single { DriverFactory(androidContext()) }
    single { PreferenceStorage(androidContext()) }
}