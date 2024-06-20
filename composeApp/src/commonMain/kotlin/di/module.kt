package di

import data.api.DndApi
import data.database.RealmDataBase
import data.database.SqlDatabase
import domain.repository.CharacterRepository
import domain.repository.MonsterRepository
import domain.repository.SpellRepository
import domain.usecase.EditCharacterUseCase
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    singleOf(::DndApi)
    single { RealmDataBase() }
    single { SqlDatabase(get()) }
}

val repositoryModule = module {
    singleOf(::SpellRepository)
    single { CharacterRepository(get(), get()) }
    single { MonsterRepository(get(), get()) }

    factory { EditCharacterUseCase(get()) }
}

expect fun platformModule(): Module