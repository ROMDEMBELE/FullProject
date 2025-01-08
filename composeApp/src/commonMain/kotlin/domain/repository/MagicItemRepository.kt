package domain.repository

import data.api.SpellApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.lighthousegames.logging.logging

class MagicItemRepository(private val api: SpellApi) {

    init {
        CoroutineScope(Dispatchers.IO).launch {
        }
    }

    companion object {
        val Log = logging("MagicItemRepository")
    }
}