package data.database.room.entity

import domain.model.Level

data class CharacterFighterEntity(
    val id: Long = 0,
    val characterId: Long, // character id
    val encounterId: Long,
    val name: String,
    val player: String,
    val initiative: Int,
    val ca: Int,
    val maxHitPoint: Int,
    val hitPoint: Int,
    val level: Level,
    val spellThrow: Int? = null,
)