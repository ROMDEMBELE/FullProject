package domain.model.campaign

import domain.model.Condition
import domain.model.monster.Monster

data class MonsterFighter(
    override val uuid: String,
    val monster: Monster,
    override val initiative: Int,
    override val conditions: List<Condition> = emptyList(),
    override val name: String = monster.name,
    override val armorClass: Int = monster.armorsClass,
    override val maxHitPoint: Int = monster.hitPoints,
    override val currentHitPoint: Int = monster.hitPoints,
    override val passivePerception: Int = monster.passivePerception
) : EncounterFighter