
import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect abstract class PlatformContext

expect interface JavaSerializable

expect fun decodeBase64ToImageBitmap(base64: String): ImageBitmap?

@OptIn(DelicateCoroutinesApi::class)
fun <T> lazyPromise(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
    return lazy {
        return@lazy GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}

expect fun randomUUID(): String