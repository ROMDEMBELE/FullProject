package domain.usecase.monster

import domain.model.monster.Challenge
import domain.repository.SettingsRepository

class ChallengeFilterUseCase(private val settingsRepository: SettingsRepository) {
    fun save(min: Challenge, max: Challenge) {
        settingsRepository.saveChallengeRange(min, max)
    }

    fun get(): ClosedFloatingPointRange<Float> {
        return settingsRepository.getChallengeRange()?.let {
            return it.first.ordinal.toFloat()..it.second.ordinal.toFloat()
        } ?: DEFAULT
    }

    companion object {
        val DEFAULT = Challenge.CR_0.ordinal.toFloat()..Challenge.CR_30.ordinal.toFloat()
    }
}