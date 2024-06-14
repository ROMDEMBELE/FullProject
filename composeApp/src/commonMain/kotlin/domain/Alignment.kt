package domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Alignment {
    @SerialName("any alignment")
    ANY_ALIGNMENT,

    @SerialName("any evil alignment")
    ANY_EVIL_ALIGNMENT,

    @SerialName("unaligned")
    UNALIGNED,

    @SerialName("chaotic evil")
    CHAOTIC_EVIL,

    @SerialName("chaotic good")
    CHAOTIC_GOOD,

    @SerialName("chaotic neutral")
    CHAOTIC_NEUTRAL,

    @SerialName("lawful evil")
    LAWFUL_EVIL,

    @SerialName("lawful good")
    LAWFUL_GOOD,

    @SerialName("lawful neutral")
    LAWFUL_NEUTRAL,

    @SerialName("neutral")
    NEUTRAL,

    @SerialName("neutral evil")
    NEUTRAL_EVIL,

    @SerialName("neutral good")
    NEUTRAL_GOOD
}