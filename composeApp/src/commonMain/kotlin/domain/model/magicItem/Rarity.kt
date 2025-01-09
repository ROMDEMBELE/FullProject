package domain.model.magicItem

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Rarity {

    @SerialName("Common")
    COMMON,

    @SerialName("Uncommon")
    UNCOMMON,

    @SerialName("Rare")
    RARE,

    @SerialName("Very Rare")
    VERY_RARE,

    @SerialName("Legendary")
    LEGENDARY,

    @SerialName("Artifact")
    ARTIFACT,

    @SerialName("Varies")
    VARIES;

}