package domain.model.monster

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
    Gargantuan,

    @SerialName("Titanic")
    Titanic;

    companion object {
        fun fromUrl(url: String): CreatureSize {
            return when {
                url.contains("tiny", ignoreCase = true) -> Tiny
                url.contains("small", ignoreCase = true) -> Small
                url.contains("medium", ignoreCase = true) -> Medium
                url.contains("large", ignoreCase = true) -> Large
                url.contains("huge", ignoreCase = true) -> Huge
                url.contains("gargantuan", ignoreCase = true) -> Gargantuan
                url.contains("titanic", ignoreCase = true) -> Titanic
                else -> throw IllegalArgumentException("Unknown creature size: $url")
            }
        }
    }
}
