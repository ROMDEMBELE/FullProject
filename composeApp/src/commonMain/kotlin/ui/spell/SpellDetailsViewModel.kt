package ui.spell

import androidx.lifecycle.ViewModel
import domain.model.spell.Spell
import domain.repository.SpellRepository

class SpellDetailsViewModel(private val spellRepository: SpellRepository) : ViewModel() {

    suspend fun getSpellDetailsByIndex(index: String): Spell? = spellRepository.getSpellByIndex(index)
}