package data.local

import org.dembeyo.shared.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import kotlin.coroutines.cancellation.CancellationException

class LocalDatasource {

    @OptIn(ExperimentalResourceApi::class)
    @Throws(NoSuchElementException::class, CancellationException::class)
    suspend fun readFile(fileName: String): String {
        return Res.readBytes("$BASE_DIR/$fileName").decodeToString()
    }

    companion object {
        private const val BASE_DIR = "files"
    }

}