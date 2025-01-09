package domain.model.monster

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class CreatureType {
    @SerialName("aberration")
    ABERRATION,

    @SerialName("beast")
    BEAST,

    @SerialName("celestial")
    CELESTIAL,

    @SerialName("construct")
    CONSTRUCT,

    @SerialName("dragon")
    DRAGON,

    @SerialName("elemental")
    ELEMENTAL,

    @SerialName("fey")
    FEY,

    @SerialName("fiend")
    FIEND,

    @SerialName("giant")
    GIANT,

    @SerialName("humanoid")
    HUMANOID,

    @SerialName("monstrosity")
    MONSTROSITY,

    @SerialName("ooze")
    OOZE,

    @SerialName("plant")
    PLANT,

    @SerialName("undead")
    UNDEAD;

    companion object {
        fun fromUrl(url: String): CreatureType {
            return when {
                url.contains("aberration", ignoreCase = true) -> ABERRATION
                url.contains("beast", ignoreCase = true) -> BEAST
                url.contains("celestial", ignoreCase = true) -> CELESTIAL
                url.contains("construct", ignoreCase = true) -> CONSTRUCT
                url.contains("dragon", ignoreCase = true) -> DRAGON
                url.contains("elemental", ignoreCase = true) -> ELEMENTAL
                url.contains("fey", ignoreCase = true) -> FEY
                url.contains("fiend", ignoreCase = true) -> FIEND
                url.contains("giant", ignoreCase = true) -> GIANT
                url.contains("humanoid", ignoreCase = true) -> HUMANOID
                url.contains("monstrosity", ignoreCase = true) -> MONSTROSITY
                url.contains("ooze", ignoreCase = true) -> OOZE
                url.contains("plant", ignoreCase = true) -> PLANT
                url.contains("undead", ignoreCase = true) -> UNDEAD
                else -> throw IllegalArgumentException("Unknown creature type: $url")
            }
        }
    }
}