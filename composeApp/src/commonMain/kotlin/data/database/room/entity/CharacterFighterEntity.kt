package data.database.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import domain.model.Level
import domain.model.encounter.Condition

@Entity(tableName = "character_fighters")
data class CharacterFighterEntity(
    @PrimaryKey(autoGenerate = true)
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
    val conditions: List<Condition>
)