package domain.model.campaign

import domain.model.Environment

data class Encounter(
    val uuid: String,
    val title: String,
    val description: String,
    val fighters: List<EncounterFighter>,
    val turn: Int,
    val isFinished: Boolean,
    val environment: Environment,
)

