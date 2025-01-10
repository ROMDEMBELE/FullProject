package di

import data.api.ChatGptApi
import data.api.FeatApi
import data.api.MonsterApi
import data.api.SpellApi
import data.api.impl.ChatGptApiImpl
import data.api.impl.FeatApiImpl
import data.api.impl.MonsterApiImpl
import data.api.impl.SpellApiImpl
import data.database.sqlDelight.SqlDatabase
import data.local.LocalDatasource
import data.repository.FeatRepositoryImpl
import data.repository.MonsterRepositoryImpl
import data.repository.SettingsRepositoryImpl
import data.repository.SpellRepositoryImpl
import domain.repository.BackgroundRepository
import domain.repository.CampaignRepository
import domain.repository.CharacterRepository
import domain.repository.EncounterRepository
import domain.repository.FeatRepository
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
import domain.usecase.encounter.AddCharacterToEncounterUseCase
import domain.usecase.encounter.AddMonsterToEncounterUseCase
import domain.usecase.encounter.CreateEncounterUseCase
import domain.usecase.encounter.DeleteEncounterUseCase
import domain.usecase.encounter.GetMainCampaignEncounterUseCase
import domain.usecase.encounter.RemoveCharacterFromEncounterUseCase
import domain.usecase.encounter.RemoveMonsterFromEncounterUseCase
import domain.usecase.encounter.UpdateEncounterUseCase
import domain.usecase.feat.SearchFeatUseCase
import domain.usecase.monster.AddMonsterToFavoriteUseCase
import domain.usecase.monster.ChallengeFilterUseCase
import domain.usecase.monster.GetFavoritesMonsterUseCase
import domain.usecase.monster.GetMonsterByKeyUseCase
import domain.usecase.monster.RemoveMonsterFromFavoriteUseCase
import domain.usecase.monster.SearchMonstersUseCase
import domain.usecase.spell.AddSpellToFavoritesUseCase
import domain.usecase.spell.GetFavoritesSpellUseCase
import domain.usecase.spell.GetSpellByKeyUseCase
import domain.usecase.spell.LevelFilterUseCase
import domain.usecase.spell.RemoveSpellFromFavoritesUseCase
import domain.usecase.spell.SearchSpellUseCase
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel.BODY
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }
            install(Logging) {
                level = BODY
                logger = object : Logger {
                    override fun log(message: String) {
                        Napier.v(tag = "Http Client", message = message)
                    }
                }

            }
            install(HttpCache) {

            }
            install(HttpTimeout) {
                requestTimeoutMillis = 5 * 60 * 1000
                socketTimeoutMillis = 5 * 60 * 1000
                connectTimeoutMillis = 5 * 60 * 1000
            }
            headers {
                append("Accept", "application/json")
                append("Content-Type", "application/json")
            }

        }.also { Napier.base(DebugAntilog()) }
    }
    single<MonsterApi> { MonsterApiImpl(get()) }
    single<SpellApi> { SpellApiImpl(get()) }
    single<FeatApi> { FeatApiImpl(get()) }
    single<ChatGptApi> { ChatGptApiImpl(get()) }
    singleOf(::LocalDatasource)

    single { SqlDatabase(get()) }
}

val repositoryModule = module {
    singleOf(::CharacterRepository)
    single<MonsterRepository> { MonsterRepositoryImpl(get(), get()) }
    single<SpellRepository> { SpellRepositoryImpl(get(), get()) }
    single<SettingsRepository> { SettingsRepositoryImpl(get()) }
    single<FeatRepository> { FeatRepositoryImpl(get(), get()) }
    singleOf(::SpeciesRepository)
    singleOf(::BackgroundRepository)
    singleOf(::CampaignRepository)
    singleOf(::MagicItemRepository)
    singleOf(::EncounterRepository)
}

val useCaseModule: Module = module {

    factoryOf(::GetMainCampaignUseCase)
    factoryOf(::GetMainCampaignCharactersUseCase)
    factoryOf(::DeleteCampaignUseCase)
    factoryOf(::SaveCampaignUseCase)

    factoryOf(::SaveCharacterUseCase)
    factoryOf(::DeleteCharacterUseCase)

    factoryOf(::SearchMonstersUseCase)
    factoryOf(::GetMonsterByKeyUseCase)
    factoryOf(::GetSpellByKeyUseCase)
    factoryOf(::AddMonsterToFavoriteUseCase)
    factoryOf(::RemoveMonsterFromFavoriteUseCase)
    factoryOf(::GetFavoritesMonsterUseCase)
    factoryOf(::ChallengeFilterUseCase)

    factoryOf(::AddSpellToFavoritesUseCase)
    factoryOf(::GetFavoritesSpellUseCase)
    factoryOf(::GetSpellByKeyUseCase)
    factoryOf(::RemoveSpellFromFavoritesUseCase)
    factoryOf(::LevelFilterUseCase)
    factoryOf(::SearchSpellUseCase)

    factoryOf(::SearchFeatUseCase)

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