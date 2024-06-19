package domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Alignment(val fullName: String) {
    @SerialName("any alignment")
    ANY_ALIGNMENT("any alignment"),

    @SerialName("any evil alignment")
    ANY_EVIL_ALIGNMENT("any evil alignment"),

    @SerialName("unaligned")
    UNALIGNED("unaligned"),

    @SerialName("chaotic evil")
    CHAOTIC_EVIL("chaotic evil"),

    @SerialName("chaotic good")
    CHAOTIC_GOOD("chaotic good"),

    @SerialName("chaotic neutral")
    CHAOTIC_NEUTRAL("chaotic neutral"),

    @SerialName("lawful evil")
    LAWFUL_EVIL("lawful evil"),

    @SerialName("lawful good")
    LAWFUL_GOOD("lawful good"),

    @SerialName("lawful neutral")
    LAWFUL_NEUTRAL("lawful neutral"),

    @SerialName("neutral")
    NEUTRAL("neutral"),

    @SerialName("neutral evil")
    NEUTRAL_EVIL("neutral evil"),

    @SerialName("neutral good")
    NEUTRAL_GOOD("neutral good");
}
