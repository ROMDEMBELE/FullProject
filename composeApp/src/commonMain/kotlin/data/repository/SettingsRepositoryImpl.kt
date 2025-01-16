package data.repository

import data.preference.PreferenceStorage
import domain.repository.SettingsRepository

class SettingsRepositoryImpl(private val storage: PreferenceStorage) : SettingsRepository {

    override fun getString(key: String, default: String): String {
        return storage.readValue<String>(key) ?: default
    }

    override fun saveString(key: String, value: String) {
        storage.storeValue(key, value)
    }

    override fun getRange(
        key: String,
        default: ClosedFloatingPointRange<Float>
    ): ClosedFloatingPointRange<Float> {
        return storage.readValue<ClosedFloatingPointRange<Float>>(key) ?: default
    }

    override fun saveRange(key: String, range: ClosedFloatingPointRange<Float>) {
        storage.storeValue(key, range)
    }

}