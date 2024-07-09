package data.database.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import domain.model.encounter.Condition
import domain.model.monster.Challenge

@Entity(tableName = "monster_fighters")
data class MonsterFighterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val monsterIndex: String,
    val encounterId: Long,
    val name: String,
    val initiative: Int,
    val ca: Int,
    val maxHitPoint: Int,
    val hitPoint: Int,
    val challenge: Challenge,
    val xp: Int,
    val conditions: List<Condition>
)