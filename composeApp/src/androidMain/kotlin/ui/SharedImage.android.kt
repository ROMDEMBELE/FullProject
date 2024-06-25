package ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.ByteArrayOutputStream

actual class SharedImage(private val bitmap: Bitmap?) {
    actual fun toByteArray(): ByteArray? {
        if (bitmap == null) return null
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }

    actual fun toImageBitmap(): ImageBitmap? = toByteArray()?.let { byteArray ->
        BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size).asImageBitmap()
    }
}