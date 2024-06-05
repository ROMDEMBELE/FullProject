package di

import data.RealmDataBase
import data.api.SpellApi
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import repository.SpellRepository

val dataModule = module {
    singleOf(::SpellApi)
    single { RealmDataBase() }
}

val repositoryModule = module {
    singleOf(::SpellRepository)
}

expect fun platformModule(): Module