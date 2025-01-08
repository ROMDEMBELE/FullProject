package domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class CreatureSize {
    @SerialName("Tiny")
    Tiny,

    @SerialName("Small")
    Small,

    @SerialName("Medium")
    Medium,

    @SerialName("Large")
    Large,

    @SerialName("Huge")
    Huge,

    @SerialName("Gargantuan")
    Gargantuan;
}
