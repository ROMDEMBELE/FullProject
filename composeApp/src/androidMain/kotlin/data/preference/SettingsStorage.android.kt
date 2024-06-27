package data.preference

import PlatformContext
import com.liftric.kvault.KVault

actual class SettingsStorage actual constructor(context: PlatformContext) {

    val store = KVault(context, FILE_NAME)

    actual inline fun <reified T> readValue(key: String): T? {
        if (store.existsObject(key)) {
            val value = when (T::class) {
                String::class -> store.string(key)
                Int::class -> store.int(key)
                Long::class -> store.long(key)
                Double::class -> store.double(key)
                Boolean::class -> store.bool(key)
                Float::class -> store.float(key)
                else -> throw IllegalArgumentException("Type not supported")
            }
            return value as? T
        } else {
            return null
        }
    }

    actual fun <T> storeValue(key: String, value: T) {
        when (value) {
            is String -> store.set(key, stringValue = value)
            is Int -> store.set(key, intValue = value)
            is Long -> store.set(key, longValue = value)
            is Double -> store.set(key, doubleValue = value)
            is Boolean -> store.set(key, boolValue = value)
            is Float -> store.set(key, floatValue = value)
            else -> throw IllegalArgumentException("Type not supported")
        }
    }

    actual fun deleteValue(key: String): Boolean = store.deleteObject(key)

    companion object {
        const val FILE_NAME = "settings"
    }

}