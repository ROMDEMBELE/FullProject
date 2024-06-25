package ui

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.get
import kotlinx.cinterop.reinterpret
import org.jetbrains.skia.Image
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation

actual class SharedImage(private val image: UIImage?) {
    @OptIn(ExperimentalForeignApi::class)
    actual fun toByteArray(): ByteArray? {
        if (image == null) return null
        val imageData = UIImageJPEGRepresentation(image, COMPRESSION_QUALITY) ?: error("image data is null")
        val bytes = imageData.bytes ?: error("image bytes is null")
        val length = imageData.length
        val data: CPointer<ByteVar> = bytes.reinterpret()
        return ByteArray(length.toInt()) { index -> data[index] }
    }

    actual fun toImageBitmap(): ImageBitmap? = toByteArray()?.let { byteArray ->
        Image.makeFromEncoded(byteArray).toComposeImageBitmap()
    }

    private companion object {
        const val COMPRESSION_QUALITY = 0.99
    }
}