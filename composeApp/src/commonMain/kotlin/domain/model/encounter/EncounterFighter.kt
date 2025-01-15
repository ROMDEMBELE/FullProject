package domain.model.encounter

import domain.model.Condition

interface EncounterFighter {
    val id: Long
    val name: String
    val initiative: Int
    val conditions: List<Condition>
    val armorClass: Int
    val maxHitPoint: Int
    val currentHitPoint: Int
    val passivePerception: Int
}