package di

import IosContext
import data.database.sqlDelight.DriverFactory
import data.preference.SettingsStorage
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ui.campaign.edit.EditCampaignViewModel
import ui.campaign.main.CampaignViewModel
import ui.character.CharacterViewModel
import ui.character.edit.EditCharacterViewModel
import ui.magicItem.details.MagicItemDetailsViewModel
import ui.magicItem.search.SearchMagicItemViewModel
import ui.monster.details.MonsterDetailsViewModel
import ui.monster.search.SearchMonsterViewModel
import ui.spell.details.SpellDetailsViewModel
import ui.spell.search.SearchSpellViewModel

actual fun platformModule(): Module = module {
    single {
        Darwin.create()
    }
    single { DriverFactory() }
    factoryOf(::SearchSpellViewModel)
    factoryOf(::SpellDetailsViewModel)
    factoryOf(::EditCharacterViewModel)
    factoryOf(::CharacterViewModel)
    factoryOf(::SearchMonsterViewModel)
    factoryOf(::MonsterDetailsViewModel)
    factoryOf(::EditCampaignViewModel)
    factoryOf(::CampaignViewModel)
    factoryOf(::SearchMagicItemViewModel)
    factoryOf(::MagicItemDetailsViewModel)

    single { SettingsStorage(IosContext) }
}

object ViewModelProvider : KoinComponent {
    fun getSpellScreenListViewModel() = SearchSpellViewModel(get(), get(), get())
    fun getSpellScreenDetailsViewModel() = SpellDetailsViewModel(get())
    fun getEditCharacterViewModel() = EditCharacterViewModel(get(), get(), get(), get(), get())
    fun getCharacterViewModel() = CharacterViewModel(get(), get())
    fun getMonsterListViewModel() = SearchMonsterViewModel(get(), get(), get(), get(), get())
    fun getMonsterDetailsViewModel() = MonsterDetailsViewModel(get())
    fun getMagicItemListViewModel() = SearchMagicItemViewModel(get())
    fun getMagicItemDetailsViewModel() = MagicItemDetailsViewModel(get())
}