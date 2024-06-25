package di

import data.api.Dnd5Api
import data.database.RealmDataBase
import data.database.SqlDatabase
import domain.repository.BackgroundRepository
import domain.repository.CharacterRepository
import domain.repository.MonsterRepository
import domain.repository.SpeciesRepository
import domain.repository.SpellRepository
import domain.usecase.DeleteCharacterUseCase
import domain.usecase.EditCharacterUseCase
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    singleOf(::Dnd5Api)
    single { RealmDataBase() }
    single { SqlDatabase(get()) }
}

val repositoryModule = module {
    singleOf(::SpellRepository)
    single { CharacterRepository(get()) }
    single { MonsterRepository(get(), get()) }
    single { SpeciesRepository(get()) }
    single { BackgroundRepository(get()) }

    factory { EditCharacterUseCase(get()) }
    factory { DeleteCharacterUseCase(get()) }
}

expect fun platformModule(): Module