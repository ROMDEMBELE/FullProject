package di

import IosContext
import data.database.room.EncounterDatabase
import data.database.room.getDatabaseBuilder
import data.database.sqlDelight.DriverFactory
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
import ui.magicItem.details.MagicItemDetailsViewModel
import ui.magicItem.list.MagicItemListViewModel
import ui.monster.details.MonsterDetailsViewModel
import ui.monster.list.MonsterListViewModel
import ui.spell.details.SpellDetailsViewModel
import ui.spell.list.SpellListViewModel

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
    factoryOf(::MagicItemListViewModel)
    factoryOf(::MagicItemDetailsViewModel)

    single { SettingsStorage(IosContext) }

    single<EncounterDatabase> { getDatabaseBuilder(IosContext) }
    single { get<EncounterDatabase>().encounterDao() }
    single { get<EncounterDatabase>().characterFighterDao() }
    single { get<EncounterDatabase>().monsterFighterDao() }
}

object ViewModelProvider : KoinComponent {
    fun getSpellScreenListViewModel() = SpellListViewModel(get(), get(), get())
    fun getSpellScreenDetailsViewModel() = SpellDetailsViewModel(get())
    fun getEditCharacterViewModel() = EditCharacterViewModel(get(), get(), get(), get(), get())
    fun getCharacterViewModel() = CharacterViewModel(get(), get())
    fun getMonsterListViewModel() = MonsterListViewModel(get(), get())
    fun getMonsterDetailsViewModel() = MonsterDetailsViewModel(get())
    fun getMagicItemListViewModel() = MagicItemListViewModel(get())
    fun getMagicItemDetailsViewModel() = MagicItemDetailsViewModel(get())
}