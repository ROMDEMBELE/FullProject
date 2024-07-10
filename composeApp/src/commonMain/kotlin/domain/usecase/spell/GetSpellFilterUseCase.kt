package domain.usecase.spell

import domain.model.Level
import domain.repository.SettingsRepository

class GetSpellFilterUseCase(private val settingsRepository: SettingsRepository) {

    operator fun invoke(): Map<Level, Boolean> {
        return settingsRepository.getSpellFilter()
            ?.split(",")
            ?.associate {
                val (key, value) = it.split(":")
                Level.valueOf(key) to value.toBoolean()
            }
            ?: emptyMap()
    }
}