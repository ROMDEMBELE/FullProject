package domain.monster

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class CreatureMovement {
    @SerialName("hover")
    HOVER,

    @SerialName("climb")
    CLIMB,

    @SerialName("walk")
    WALK,

    @SerialName("fly")
    FLY,

    @SerialName("swim")
    SWIM
}