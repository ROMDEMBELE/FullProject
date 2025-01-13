package domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Condition(val url: String) {

    @SerialName("Blinded")
    BLINDED("https://api.open5e.com/v2/conditions/blinded"),

    @SerialName("Charmed")
    CHARMED("https://api.open5e.com/v2/conditions/charmed"),

    @SerialName("Deafened")
    DEAFENED("https://api.open5e.com/v2/conditions/deafened"),

    @SerialName("Exhaustion")
    EXHAUSTION("https://api.open5e.com/v2/conditions/exhaustion"),

    @SerialName("Frightened")
    FRIGHTENED("https://api.open5e.com/v2/conditions/frightened"),

    @SerialName("Grappled")
    GRAPPLED("https://api.open5e.com/v2/conditions/grappled"),

    @SerialName("Incapacitated")
    INCAPACITATED("https://api.open5e.com/v2/conditions/incapacitated"),

    @SerialName("Invisible")
    INVISIBLE("https://api.open5e.com/v2/conditions/invisible"),

    @SerialName("Paralyzed")
    PARALYZED("https://api.open5e.com/v2/conditions/paralyzed"),

    @SerialName("Petrified")
    PETRIFIED("https://api.open5e.com/v2/conditions/petrified"),

    @SerialName("Poisoned")
    POISONED("https://api.open5e.com/v2/conditions/poisoned"),

    @SerialName("Prone")
    PRONE("https://api.open5e.com/v2/conditions/prone"),

    @SerialName("Restrained")
    RESTRAINED("https://api.open5e.com/v2/conditions/restrained"),

    @SerialName("Stunned")
    STUNNED("https://api.open5e.com/v2/conditions/stunned"),

    @SerialName("Unconscious")
    UNCONSCIOUS("https://api.open5e.com/v2/conditions/unconscious");

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