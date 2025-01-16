package domain.usecase.spell

import androidx.compose.ui.text.input.TextFieldValue
import domain.model.Level
import domain.model.spell.Spell
import domain.repository.SettingsRepository
import domain.repository.SpellRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchSpellUseCase(
    private val spellRepository: SpellRepository,
    private val settingsRepository: SettingsRepository
) {

    suspend operator fun invoke(
        query: TextFieldValue,
        minLevel: Level,
        maxLevel: Level
    ): Flow<List<Spell>> {
        // Save the search criteria in the settings
        val range = minLevel.ordinal.toFloat()..maxLevel.ordinal.toFloat()
        settingsRepository.saveRange(SettingsRepository.SEARCH_SPELL_LEVEL_RANGE, range)

        val listOfSpells: MutableSet<Spell> = mutableSetOf()

        return spellRepository.search(query.text, minLevel, maxLevel).map { newSpells ->
            listOfSpells.addAll(newSpells)
            listOfSpells.toList().distinctBy { it.name }
        }
    }

}