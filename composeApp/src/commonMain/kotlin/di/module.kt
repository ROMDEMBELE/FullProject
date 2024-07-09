package di

import data.api.Dnd5Api
import data.database.SqlDatabase
import data.database.realm.RealmDataBase
import domain.repository.BackgroundRepository
import domain.repository.CampaignRepository
import domain.repository.CharacterRepository
import domain.repository.MagicItemRepository
import domain.repository.MonsterRepository
import domain.repository.SettingsRepository
import domain.repository.SpeciesRepository
import domain.repository.SpellRepository
import domain.usecase.campaign.*
import domain.usecase.character.*
import domain.usecase.common.*
import domain.usecase.encounter.*
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    singleOf(::Dnd5Api)
    single { RealmDataBase() }
    single { SqlDatabase(get()) }
}

val repositoryModule = module {
    singleOf(::SpellRepository)
    singleOf(::CharacterRepository)
    singleOf(::MonsterRepository)
    singleOf(::SpeciesRepository)
    singleOf(::BackgroundRepository)
    singleOf(::CampaignRepository)
    singleOf(::SettingsRepository)
    singleOf(::MagicItemRepository)

    factoryOf(::GetMainCampaignUseCase)
    factoryOf(::GetMainCampaignCharactersUseCase)
    factoryOf(::DeleteCampaignUseCase)
    factoryOf(::SaveCampaignUseCase)

    factoryOf(::SaveCharacterUseCase)
    factoryOf(::DeleteCharacterUseCase)

    factoryOf(::AddToFavoriteUseCase)
    factoryOf(::RemoveFromFavoriteUseCase)

    factoryOf(::AddCharacterToEncounterUseCase)
    factoryOf(::AddMonsterToEncounterUseCase)
    factoryOf(::AddMonsterToEncounterUseCase)
    factoryOf(::RemoveCharacterFromEncounterUseCase)
    factoryOf(::RemoveMonsterFromEncounterUseCase)
    factoryOf(::CreateEncounterUseCase)
    factoryOf(::UpdateEncounterUseCase)
}

expect fun platformModule(): Module