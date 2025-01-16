package domain.model.campaign

import domain.model.Condition
import domain.model.character.Character

data class CharacterFighter(
    override val uuid: String,
    val character: Character,
    override val initiative: Int,
    override val conditions: List<Condition> = emptyList(),
    override val name: String = character.name,
    override val maxHitPoint: Int = character.hitPoint,
    override val currentHitPoint: Int = character.hitPoint,
    override val armorClass: Int = character.armorClass,
    override val passivePerception: Int = character.passivePerception
) : EncounterFighter