package domain.model.encounter

import domain.model.Environment

data class Encounter(
    val id: Long,
    val title: String,
    val description: String,
    val fighters: List<EncounterFighter>,
    val turn: Int,
    val isFinished: Boolean,
    val environment: Environment,
)

