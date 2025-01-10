package domain.usecase.spell

import domain.model.Level
import domain.repository.SettingsRepository

class LevelFilterUseCase(private val settingsRepository: SettingsRepository) {

    fun save(min: Level, max: Level) {
        settingsRepository.saveLevelRange(min, max)
    }

    fun get(): ClosedFloatingPointRange<Float> {
        return settingsRepository.getLevelRange()?.let {
            return it.first.ordinal.toFloat()..it.second.ordinal.toFloat()
        } ?: DEFAULT
    }

    companion object {
        val DEFAULT = Level.LEVEL_0.ordinal.toFloat()..Level.LEVEL_9.ordinal.toFloat()
    }


}