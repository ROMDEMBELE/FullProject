interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect abstract class PlatformContext

expect interface JavaSerializable