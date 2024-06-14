package domain.monster

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class CreatureSense {
    @SerialName("darkvision")
    DARKVISION,

    @SerialName("blindsight")
    BLINDSIGHT,

    @SerialName("truesight")
    TRUESIGHT,

    @SerialName("passive_perception")
    PASSIVE_PERCEPTION
}