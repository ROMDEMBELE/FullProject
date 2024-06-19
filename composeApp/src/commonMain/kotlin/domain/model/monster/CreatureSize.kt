package domain.model.monster

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class CreatureSize(val fullName: String) {
    @SerialName("Tiny")
    TINY("Tiny"),

    @SerialName("Small")
    SMALL("Small"),

    @SerialName("Medium")
    MEDIUM("Medium"),

    @SerialName("Large")
    LARGE("Large"),

    @SerialName("Huge")
    HUGE("Huge"),

    @SerialName("Gargantuan")
    GARGANTUAN("Gargantuan");
}
