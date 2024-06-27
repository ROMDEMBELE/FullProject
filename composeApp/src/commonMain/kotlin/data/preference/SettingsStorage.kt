package data.preference

import PlatformContext

expect class SettingsStorage(context: PlatformContext) {

    inline fun <reified T> readValue(key: String): T?

    fun <T> storeValue(key: String, value: T)

    fun deleteValue(key: String): Boolean

}