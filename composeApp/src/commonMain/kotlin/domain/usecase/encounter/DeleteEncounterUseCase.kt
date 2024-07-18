package domain.usecase.encounter

import domain.model.encounter.CharacterFighter
import domain.model.encounter.Encounter
import domain.model.encounter.MonsterFighter
import domain.repository.EncounterRepository

class DeleteEncounterUseCase(private val encounterRepository: EncounterRepository) {

    suspend operator fun invoke(encounter: Encounter, force: Boolean = false) {
        if (force) {
            encounter.fighters.forEach {
                when (it) {
                    is CharacterFighter -> encounterRepository.deleteCharacterFighter(it.id)
                    is MonsterFighter -> encounterRepository.deleteMonsterFighter(it.id)
                }
            }
            encounterRepository.delete(encounter.id)
        } else if (encounter.fighters.isNotEmpty()) {
            throw IllegalStateException("Encounter is not empty, Please delete all related fighters before")
        } else {
            encounterRepository.delete(encounter.id)
        }
    }
}