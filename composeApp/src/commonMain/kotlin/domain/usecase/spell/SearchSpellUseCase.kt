package domain.usecase.spell

import androidx.compose.ui.text.input.TextFieldValue
import domain.model.Level
import domain.model.spell.Spell
import domain.repository.SpellRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchSpellUseCase(private val spellRepository: SpellRepository) {

    suspend operator fun invoke(
        query: TextFieldValue,
        minLevel: Level,
        maxLevel: Level
    ): Flow<List<Spell>> {
        val listOfSpells: MutableSet<Spell> = mutableSetOf()
        return spellRepository.search(query.text, minLevel, maxLevel).map { newSpells ->
            listOfSpells.addAll(newSpells)
            listOfSpells.toList().distinctBy { it.name }
        }
    }

}