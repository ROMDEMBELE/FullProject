package domain.repository

import data.preference.SettingsStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SettingsRepository(private val settingsStorage: SettingsStorage) {

    private val mainCampaignId =
        MutableStateFlow<Long?>(settingsStorage.readValue(CURRENT_CAMPAIGN_ID))

    fun setMainCampaignId(id: Long?) {
        if (id == null) {
            settingsStorage.deleteValue(CURRENT_CAMPAIGN_ID)
        } else {
            settingsStorage.storeValue(CURRENT_CAMPAIGN_ID, id)
        }
        mainCampaignId.update { id }
    }

    fun getMainCampaignId(): Flow<Long?> = mainCampaignId.asStateFlow()

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