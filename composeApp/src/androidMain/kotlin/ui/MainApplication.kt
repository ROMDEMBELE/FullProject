package ui

import android.app.Application
import di.dataModule
import di.platformModule
import di.repositoryModule
import di.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(listOf(dataModule, repositoryModule, useCaseModule, platformModule()))
            androidContext(this@MainApplication)
        }
    }
}