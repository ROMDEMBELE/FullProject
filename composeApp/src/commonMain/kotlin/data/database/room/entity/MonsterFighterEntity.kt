package data.database.room.entity

import domain.model.monster.Challenge

data class MonsterFighterEntity(
    val id: Long = 0,
    val monsterIndex: String,
    val encounterId: Long,
    val name: String,
    val initiative: Int,
    val ca: Int,
    val maxHitPoint: Int,
    val hitPoint: Int,
    val challenge: Challenge,
)