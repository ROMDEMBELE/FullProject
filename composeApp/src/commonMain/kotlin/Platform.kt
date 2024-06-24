import androidx.compose.ui.graphics.ImageBitmap

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect abstract class PlatformContext

expect interface JavaSerializable

expect fun decodeBase64ToImageBitmap(base64: String): ImageBitmap?