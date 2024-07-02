package di

import data.api.Dnd5Api
import data.database.RealmDataBase
import data.database.SqlDatabase
import domain.repository.BackgroundRepository
import domain.repository.CampaignRepository
import domain.repository.CharacterRepository
import domain.repository.ConditionRepository
import domain.repository.MonsterRepository
import domain.repository.SettingsRepository
import domain.repository.SpeciesRepository
import domain.repository.SpellRepository
import domain.usecase.DeleteCampaignUseCase
import domain.usecase.DeleteCharacterUseCase
import domain.usecase.GetCampaignCharacterUseCase
import domain.usecase.GetCampaignsUseCase
import domain.usecase.SaveCampaignUseCase
import domain.usecase.SaveCharacterUseCase
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
    singleOf(::ConditionRepository)
    singleOf(::SettingsRepository)

    factoryOf(::SaveCharacterUseCase)
    factoryOf(::DeleteCharacterUseCase)
    factoryOf(::GetCampaignsUseCase)
    factoryOf(::GetCampaignCharacterUseCase)
    factoryOf(::DeleteCampaignUseCase)
    factoryOf(::SaveCampaignUseCase)
}

expect fun platformModule(): Module