package domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Environment {
    @SerialName("arctic")
    ARCTIC,

    @SerialName("coastal")
    COASTAL,

    @SerialName("desert")
    DESERT,

    @SerialName("forest")
    FOREST,

    @SerialName("grassland")
    GRASSLAND,

    @SerialName("hill")
    HILL,

    @SerialName("mountain")
    MOUNTAIN,

    @SerialName("swamp")
    SWAMP,

    @SerialName("underdark")
    UNDERDARK,

    @SerialName("urban")
    URBAN,

    @SerialName("underwater")
    UNDERWATER

    companion object {
        fun fromString(value: String): Environment {
            return when (value) {
                "arctic" -> ARCTIC
                "coastal" -> COASTAL
                "desert" -> DESERT
                "forest" -> FOREST
                "grassland" -> GRASSLAND
                "hill" -> HILL
                "mountain" -> MOUNTAIN
                "swamp" -> SWAMP
                "underdark" -> UNDERDARK
                "urban" -> URBAN
                "underwater" -> UNDERWATER
                else -> throw IllegalArgumentException("Unknown environment: $value")
            }
        }
    }
}