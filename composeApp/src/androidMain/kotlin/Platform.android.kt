import android.graphics.BitmapFactory
import android.os.Build
import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.util.UUID

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()


actual typealias PlatformContext = android.content.Context

actual typealias JavaSerializable = java.io.Serializable

actual fun decodeBase64ToImageBitmap(base64: String): ImageBitmap? {
    return try {
        val imageBytes = Base64.decode(base64, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        bitmap.asImageBitmap()
    } catch (e: Exception) {
        null
    }
}

actual fun randomUUID() = UUID.randomUUID().toString()
