package domain.model

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
    UNDEAD
}