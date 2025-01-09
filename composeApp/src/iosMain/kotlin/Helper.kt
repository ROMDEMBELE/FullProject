import di.dataModule
import di.platformModule
import di.repositoryModule
import di.useCaseModule
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(listOf(dataModule, repositoryModule, useCaseModule, platformModule()))
    }
}