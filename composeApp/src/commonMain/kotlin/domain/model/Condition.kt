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
    UNCONSCIOUS;

    companion object {
        fun fromUrl(url: String): Condition {
            return when {
                url.contains("blinded", ignoreCase = true) -> BLINDED
                url.contains("charmed", ignoreCase = true) -> CHARMED
                url.contains("deafened", ignoreCase = true) -> DEAFENED
                url.contains("exhaustion", ignoreCase = true) -> EXHAUSTION
                url.contains("frightened", ignoreCase = true) -> FRIGHTENED
                url.contains("grappled", ignoreCase = true) -> GRAPPLED
                url.contains("incapacitated", ignoreCase = true) -> INCAPACITATED
                url.contains("invisible", ignoreCase = true) -> INVISIBLE
                url.contains("paralyzed", ignoreCase = true) -> PARALYZED
                url.contains("petrified", ignoreCase = true) -> PETRIFIED
                url.contains("poisoned", ignoreCase = true) -> POISONED
                url.contains("prone", ignoreCase = true) -> PRONE
                url.contains("restrained", ignoreCase = true) -> RESTRAINED
                url.contains("stunned", ignoreCase = true) -> STUNNED
                url.contains("unconscious", ignoreCase = true) -> UNCONSCIOUS
                else -> throw IllegalArgumentException("Unknown condition: $url")
            }
        }
    }
}