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

    fun setSpellFilter(filter: String) {
        settingsStorage.storeValue(SPELL_FILTER, filter)
    }

    fun getSpellFilter(): String? = settingsStorage.readValue<String>(SPELL_FILTER)

    fun setChallengeFilter(filter: String) {
        settingsStorage.storeValue(CHALLENGE_FILTER, filter)
    }

    fun getChallengeFilter(): String? = settingsStorage.readValue(CHALLENGE_FILTER)

    companion object {
        private const val CURRENT_CAMPAIGN_ID = "current_campaign_id"
        private const val SPELL_FILTER = "spell_filter"
        private const val CHALLENGE_FILTER = "challenge_filter"
    }
}