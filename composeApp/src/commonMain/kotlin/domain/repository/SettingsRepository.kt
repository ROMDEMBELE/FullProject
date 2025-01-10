package domain.repository

import domain.model.Level
import domain.model.monster.Challenge

interface SettingsRepository {

    fun getLevelRange(): Pair<Level, Level>?

    fun saveLevelRange(min: Level, max: Level)

    fun getChallengeRange(): Pair<Challenge, Challenge>?

    fun saveChallengeRange(min: Challenge, max: Challenge)

}