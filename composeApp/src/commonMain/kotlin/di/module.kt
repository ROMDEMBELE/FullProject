package di

import data.api.Dnd5Api
import data.database.realm.RealmDataBase
import data.database.room.EncounterDatabase
import data.database.room.getDatabase
import data.database.room.getDatabaseBuilder
import data.database.sqlDelight.SqlDatabase
import domain.repository.BackgroundRepository
import domain.repository.CampaignRepository
import domain.repository.CharacterRepository
import domain.repository.EncounterRepository
import domain.repository.MagicItemRepository
import domain.repository.MonsterRepository
import domain.repository.SettingsRepository
import domain.repository.SpeciesRepository
import domain.repository.SpellRepository
import domain.usecase.campaign.DeleteCampaignUseCase
import domain.usecase.campaign.GetCampaignsUseCase
import domain.usecase.campaign.GetMainCampaignUseCase
import domain.usecase.campaign.SaveCampaignUseCase
import domain.usecase.character.DeleteCharacterUseCase
import domain.usecase.character.GetMainCampaignCharactersUseCase
import domain.usecase.character.SaveCharacterUseCase
import domain.usecase.common.AddToFavoriteUseCase
import domain.usecase.common.RemoveFromFavoriteUseCase
import domain.usecase.encounter.AddCharacterToEncounterUseCase
import domain.usecase.encounter.AddMonsterToEncounterUseCase
import domain.usecase.encounter.CreateEncounterUseCase
import domain.usecase.encounter.DeleteEncounterUseCase
import domain.usecase.encounter.GetMainCampaignEncounterUseCase
import domain.usecase.encounter.RemoveCharacterFromEncounterUseCase
import domain.usecase.encounter.RemoveMonsterFromEncounterUseCase
import domain.usecase.encounter.UpdateEncounterUseCase
import domain.usecase.monster.ChallengeFilterUseCase
import domain.usecase.spell.GetSpellFilterUseCase
import domain.usecase.spell.SaveSpellFilterUseCase
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    singleOf(::Dnd5Api)
    single { RealmDataBase() }
    single { SqlDatabase(get()) }
    single {
        val builder = getDatabaseBuilder(get())
        getDatabase(builder)
    }
    single { get<EncounterDatabase>().encounterDao() }
    single { get<EncounterDatabase>().characterFighterDao() }
    single { get<EncounterDatabase>().monsterFighterDao() }
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
    singleOf(::EncounterRepository)

    factoryOf(::GetMainCampaignUseCase)
    factoryOf(::GetMainCampaignCharactersUseCase)
    factoryOf(::DeleteCampaignUseCase)
    factoryOf(::SaveCampaignUseCase)

    factoryOf(::SaveCharacterUseCase)
    factoryOf(::DeleteCharacterUseCase)

    factoryOf(::AddToFavoriteUseCase)
    factoryOf(::RemoveFromFavoriteUseCase)

    factory { ChallengeFilterUseCase(get()) }

    factory { SaveSpellFilterUseCase(get()) }
    factory { GetSpellFilterUseCase(get()) }

    factoryOf(::AddCharacterToEncounterUseCase)
    factoryOf(::AddMonsterToEncounterUseCase)
    factoryOf(::AddMonsterToEncounterUseCase)
    factoryOf(::RemoveCharacterFromEncounterUseCase)
    factoryOf(::RemoveMonsterFromEncounterUseCase)
    factoryOf(::CreateEncounterUseCase)
    factoryOf(::UpdateEncounterUseCase)
    factoryOf(::GetMainCampaignEncounterUseCase)
    factoryOf(::GetCampaignsUseCase)
    factoryOf(::DeleteEncounterUseCase)
}

expect fun platformModule(): Module