package domain.usecase.spell

import domain.model.Level
import domain.repository.SettingsRepository

class SaveSpellFilterUseCase(private val settingsRepository: SettingsRepository) {

    operator fun invoke(filter: Map<Level, Boolean>) {
        val string = filter.map { "${it.key.name}:${it.value}" }.joinToString(",")
        settingsRepository.setSpellFilter(string)
    }

}