package domain.usecase.monster

import domain.model.monster.Challenge
import domain.repository.SettingsRepository

class ChallengeFilterUseCase(private val settingsRepository: SettingsRepository) {
    fun save(filter: ClosedFloatingPointRange<Float>) {
        settingsRepository.setChallengeFilter("${filter.start}:${filter.endInclusive}")
    }

    fun get(): ClosedFloatingPointRange<Float> {
        val filter = settingsRepository.getChallengeFilter()
        return if (filter == null) {
            Challenge.CR_0.ordinal.toFloat()..Challenge.CR_30.ordinal.toFloat()
        } else {
            val (start, end) = filter.split(":")
            start.toFloat()..end.toFloat()
        }
    }
}