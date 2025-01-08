package domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Condition {

    @SerialName("Blinded")
    BLINDED,

    @SerialName("Charmed")
    CHARMED,

    @SerialName("Deafened")
    DEAFENED,

    @SerialName("Exhaustion")
    EXHAUSTION,

    @SerialName("Frightened")
    FRIGHTENED,

    @SerialName("Grappled")
    GRAPPLED,

    @SerialName("Incapacitated")
    INCAPACITATED,

    @SerialName("Invisible")
    INVISIBLE,

    @SerialName("Paralyzed")
    PARALYZED,

    @SerialName("Petrified")
    PETRIFIED,

    @SerialName("Poisoned")
    POISONED,

    @SerialName("Prone")
    PRONE,

    @SerialName("Restrained")
    RESTRAINED,

    @SerialName("Stunned")
    STUNNED,

    @SerialName("Unconscious")
    UNCONSCIOUS
}