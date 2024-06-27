package domain.repository

import data.preference.SettingsStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SettingsRepository(private val settingsStorage: SettingsStorage) {

    private val currentCampaignId: MutableStateFlow<Long?> =
        MutableStateFlow(settingsStorage.readValue(CURRENT_CAMPAIGN_ID) as Long?)

    fun setCurrentCampaignId(id: Long) {
        settingsStorage.storeValue(CURRENT_CAMPAIGN_ID, id)
        currentCampaignId.update { id }
    }

    fun getCurrentCampaignId(): Flow<Long?> = currentCampaignId.asStateFlow()

    companion object {
        private const val CURRENT_CAMPAIGN_ID = "current_campaign_id"
    }
}