package data.repository

import data.preference.PreferenceStorage
import domain.model.Level
import domain.model.monster.Challenge
import domain.repository.SettingsRepository

class SettingsRepositoryImpl(private val storage: PreferenceStorage) : SettingsRepository {

    override fun getLevelRange(): Pair<Level, Level>? {
        val min = storage.readValue<String>(MIN_LEVEL_RANGE)?.let { Level.valueOf(it) }
        val max = storage.readValue<String>(MAX_LEVEL_RANGE)?.let { Level.valueOf(it) }
        if (min == null || max == null) return null
        return min to max
    }

    override fun saveLevelRange(min: Level, max: Level) {
        storage.storeValue(MIN_LEVEL_RANGE, min.name)
        storage.storeValue(MAX_LEVEL_RANGE, max.name)
    }

    override fun getChallengeRange(): Pair<Challenge, Challenge>? {
        val min = storage.readValue<String>(MIN_CHALLENGE_RANGE)?.let { Challenge.valueOf(it) }
        val max = storage.readValue<String>(MAX_CHALLENGE_RANGE)?.let { Challenge.valueOf(it) }
        if (min == null || max == null) return null
        return min to max
    }

    override fun saveChallengeRange(min: Challenge, max: Challenge) {
        storage.storeValue(MIN_CHALLENGE_RANGE, min.name)
        storage.storeValue(MAX_CHALLENGE_RANGE, max.name)
    }

    companion object {
        private const val CURRENT_CAMPAIGN_ID = "current_campaign_id"
        private const val MIN_LEVEL_RANGE = "min_level_range"
        private const val MAX_LEVEL_RANGE = "max_level_range"
        private const val MIN_CHALLENGE_RANGE = "min_challenge_range"
        private const val MAX_CHALLENGE_RANGE = "max_challenge_range"
    }
}