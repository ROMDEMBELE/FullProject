
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import io.ktor.util.decodeBase64Bytes
import org.jetbrains.skia.Image
import platform.Foundation.NSUUID
import platform.Foundation.NSUserDefaults
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual abstract class PlatformContext

object IosContext : PlatformContext()

actual interface JavaSerializable

actual fun decodeBase64ToImageBitmap(base64: String): ImageBitmap? {
    return Image.makeFromEncoded(base64.decodeBase64Bytes()).toComposeImageBitmap()
}

actual fun randomUUID(): String = NSUUID().UUIDString()

actual fun changeLanguage(language: String) {
    NSUserDefaults.standardUserDefaults.setObject(arrayListOf(language),"AppleLanguages")
}