package ui

import android.app.Application
import di.dataModule
import di.platformModule
import di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(listOf(dataModule, repositoryModule, platformModule()))
            androidContext(this@MyApplication)
        }
    }
}