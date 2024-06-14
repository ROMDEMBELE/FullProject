package domain.monster

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class CreatureSize {
    @SerialName("Tiny")
    TINY,

    @SerialName("Small")
    SMALL,

    @SerialName("Medium")
    MEDIUM,

    @SerialName("Large")
    LARGE,

    @SerialName("Huge")
    HUGE,

    @SerialName("Gargantuan")
    GARGANTUAN;
}