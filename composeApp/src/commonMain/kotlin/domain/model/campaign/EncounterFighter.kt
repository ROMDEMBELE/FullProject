package domain.model.campaign

import domain.model.Condition

interface EncounterFighter {
    val uuid: String
    val name: String
    val initiative: Int
    val conditions: List<Condition>
    val armorClass: Int
    val maxHitPoint: Int
    val currentHitPoint: Int
    val passivePerception: Int
}