package di

import data.RealmDataBase
import data.api.DndApi
import data.sql_database.Database
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import repository.CharacterRepository
import repository.SpellRepository

val dataModule = module {
    singleOf(::DndApi)
    single { RealmDataBase() }
    single { Database(get()) }
}

val repositoryModule = module {
    singleOf(::SpellRepository)
    single { CharacterRepository(get(), get()) }
}

expect fun platformModule(): Module