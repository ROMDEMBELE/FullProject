package domain.model.encounter

import domain.model.Level
import domain.model.monster.Challenge

data class Encounter(
    val id: Long,
    val campaignId: Long,
    val title: String,
    val description: String,
    val fighters: List<EncounterFighter>,
    val turn: Int,
    val isFinished: Boolean,
    val inProgress: Boolean
)

abstract class EncounterFighter {
    abstract val id: Long
    abstract val name: String
    abstract val initiative: Int
    abstract val conditions: List<Condition>
    abstract val armorClass: Int
    abstract val spellSavingThrow: Int?
    abstract val maxHitPoint: Int
    abstract val currentHitPoint: Int
}

data class CharacterFighter(
    override val id: Long,
    override val conditions: List<Condition> = emptyList(),
    override val name: String,
    override val maxHitPoint: Int,
    override val initiative: Int,
    override val armorClass: Int,
    override val spellSavingThrow: Int?,
    override val currentHitPoint: Int,
    val characterId: Long,
    val player: String,
    val level: Level,
    val ca: Int,
) : EncounterFighter()

data class MonsterFighter(
    override val id: Long,
    override val name: String,
    override val initiative: Int,
    override val conditions: List<Condition>,
    override val armorClass: Int,
    override val spellSavingThrow: Int?,
    override val maxHitPoint: Int,
    override val currentHitPoint: Int,
    val index: String,
    val xp: Int,
    val challenge: Challenge
) : EncounterFighter()