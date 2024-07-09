package domain.repository

import data.preference.SettingsStorage

class SettingsRepository(private val settingsStorage: SettingsStorage) {

    fun setMainCampaignId(id: Long?) {
        if (id == null) {
            settingsStorage.deleteValue(CURRENT_CAMPAIGN_ID)
        } else {
            settingsStorage.storeValue(CURRENT_CAMPAIGN_ID, id)
        }
    }

    fun getMainCampaignId(): Long? = settingsStorage.readValue(CURRENT_CAMPAIGN_ID)

    companion object {
        private const val CURRENT_CAMPAIGN_ID = "current_campaign_id"
    }
}