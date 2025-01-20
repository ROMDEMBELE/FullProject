package di

import data.database.sqlDelight.DriverFactory
import data.preference.PreferenceStorage
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ui.campaign.save.SaveCampaignViewModel
import ui.campaign.search.AdventurePagerViewModel
import ui.character.search.SearchCharacterViewModel
import ui.character.save.SaveCharacterViewModel
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
    viewModelOf(::SaveCharacterViewModel)
    viewModelOf(::SearchMonsterViewModel)
    viewModelOf(::MonsterDetailsViewModel)
    viewModelOf(::SearchFeatViewModel)
    viewModelOf(::SearchCharacterViewModel)
    viewModelOf(::SaveCampaignViewModel)
    viewModelOf(::AdventurePagerViewModel)

    viewModelOf(::SearchMagicItemViewModel)
    viewModelOf(::MagicItemDetailsViewModel)
    viewModelOf(::EncounterListViewModel)

    single { DriverFactory(androidContext()) }
    single { PreferenceStorage(androidContext()) }
}