package domain.usecase.settings

import domain.repository.SettingsRepository

class GetLevelFilterUseCase(private val settingsRepository: SettingsRepository) {

    operator fun invoke(): ClosedFloatingPointRange<Float> = settingsRepository.getRange(
        SettingsRepository.SEARCH_SPELL_LEVEL_RANGE,
        SettingsRepository.DEFAULT_LEVEL_RANGE
    )

}