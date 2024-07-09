package data.database.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "encounters")
data class EncounterEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val campaignId: Long,
    val title: String,
    val description: String,
    val turn: Int = 0,
    val isFinished: Boolean = true
)