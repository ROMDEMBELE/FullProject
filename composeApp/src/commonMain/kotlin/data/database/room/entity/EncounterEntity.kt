package data.database.room.entity

data class EncounterEntity(
    val campaignId: Long,
    val title: String,
    val description: String,
    val turn: Int = 0,
    val isFinished: Boolean = true,
    val inProgress: Boolean = false
)