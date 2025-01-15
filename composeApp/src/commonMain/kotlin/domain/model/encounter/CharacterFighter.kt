package domain.model.encounter

import domain.model.Condition
import domain.model.character.Character

data class CharacterFighter(
    override val id: Long,
    override val initiative: Int,
    val character: Character,
    override val conditions: List<Condition> = emptyList(),
    override val name: String = character.fullName,
    override val maxHitPoint: Int = character.hitPoint,
    override val currentHitPoint: Int = character.hitPoint,
    override val armorClass: Int = character.armorClass,
    override val passivePerception: Int = character.passivePerception
) : EncounterFighter