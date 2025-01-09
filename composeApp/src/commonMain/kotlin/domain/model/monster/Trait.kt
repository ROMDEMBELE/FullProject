package domain.model.monster

import kotlinx.serialization.Serializable

@Serializable
data class Trait(
    val name: String,
    val desc: String
)
