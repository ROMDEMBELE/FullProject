package domain.usecase.settings

import domain.repository.SettingsRepository

class GetChallengeFilterUseCase(private val settingsRepository: SettingsRepository) {

    operator fun invoke(): ClosedFloatingPointRange<Float> = settingsRepository.getRange(
        SettingsRepository.SEARCH_MONSTER_CHALLENGE_RANGE,
        SettingsRepository.DEFAULT_CHALLENGE_RANGE
    )
}