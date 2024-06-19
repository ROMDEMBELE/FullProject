import android.os.Build

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()


actual typealias PlatformContext = android.content.Context

actual typealias JavaSerializable = java.io.Serializable