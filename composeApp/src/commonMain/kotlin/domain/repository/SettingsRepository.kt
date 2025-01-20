package domain.repository

import domain.model.Level
import domain.model.monster.Challenge

interface SettingsRepository {

    fun getString(key: String, default: String? = null): String?

    fun saveString(key: String, value: String)

    fun getRange(
        key: String,
        default: ClosedFloatingPointRange<Float>
    ): ClosedFloatingPointRange<Float>

    fun saveRange(key: String, range: ClosedFloatingPointRange<Float>)

    companion object {
        const val CAMPAIGN_ID = "CAMPAIGN_ID"
        const val SEARCH_SPELL_LEVEL_RANGE = "SEARCH_SPELL_LEVEL_RANGE"
        const val SEARCH_MONSTER_CHALLENGE_RANGE = "SEARCH_MONSTER_CHALLENGE_RANGE"

        val DEFAULT_LEVEL_RANGE: ClosedFloatingPointRange<Float> =
            Level.LEVEL_0.ordinal.toFloat()..Level.LEVEL_9.ordinal.toFloat()

        val DEFAULT_CHALLENGE_RANGE: ClosedFloatingPointRange<Float> =
            Challenge.CR_0.ordinal.toFloat()..Challenge.CR_30.ordinal.toFloat()
    }

}