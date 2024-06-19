package di

import data.database.RealmDataBase
import data.api.DndApi
import data.database.SqlDatabase
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import domain.repository.CharacterRepository
import domain.repository.MonsterRepository
import domain.repository.SpellRepository

val dataModule = module {
    singleOf(::DndApi)
    single { RealmDataBase() }
    single { SqlDatabase(get()) }
}

val repositoryModule = module {
    singleOf(::SpellRepository)
    single { CharacterRepository(get(), get()) }
    single { MonsterRepository(get(), get()) }
}

expect fun platformModule(): Module