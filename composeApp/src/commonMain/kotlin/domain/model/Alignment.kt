package domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Alignment {
    @SerialName("any alignment")
    AnyAlignment,

    @SerialName("any evil alignment")
    AnyEvilAlignment,

    @SerialName("unaligned")
    Unaligned,

    @SerialName("chaotic evil")
    ChaoticEvil,

    @SerialName("chaotic good")
    ChaoticGood,

    @SerialName("chaotic neutral")
    ChaoticNeutral,

    @SerialName("lawful evil")
    LawfulEvil,

    @SerialName("lawful good")
    LawfulGood,

    @SerialName("lawful neutral")
    LawfulNeutral,

    @SerialName("neutral")
    Neutral,

    @SerialName("neutral evil")
    NeutralEvil,

    @SerialName("neutral good")
    NeutralGood;
}