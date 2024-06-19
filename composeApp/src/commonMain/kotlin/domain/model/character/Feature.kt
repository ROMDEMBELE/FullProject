package domain.model.character

import domain.model.Level

data class Feature(
    val index: String,
    val name: String,
    val classIndex: String? = null,
    val level: Level? = null,
    val desc: String? = null
)