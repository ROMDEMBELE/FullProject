package domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class DamageType {

    @SerialName("acid")
    ACID,

    @SerialName("bludgeoning")
    BLUDGEONING,

    @SerialName("cold")
    COLD,

    @SerialName("fire")
    FIRE,

    @SerialName("force")
    FORCE,

    @SerialName("lightning")
    LIGHTNING,

    @SerialName("necrotic")
    NECROTIC,

    @SerialName("piercing")
    PIERCING,

    @SerialName("poison")
    POISON,

    @SerialName("psychic")
    PSYCHIC,

    @SerialName("radiant")
    RADIANT,

    @SerialName("slashing")
    SLASHING,

    @SerialName("thunder")
    THUNDER;

    companion object {
        fun fromString(value: String): DamageType {
            return when (value) {
                "acid" -> ACID
                "bludgeoning" -> BLUDGEONING
                "cold" -> COLD
                "fire" -> FIRE
                "force" -> FORCE
                "lightning" -> LIGHTNING
                "necrotic" -> NECROTIC
                "piercing" -> PIERCING
                "poison" -> POISON
                "psychic" -> PSYCHIC
                "radiant" -> RADIANT
                "slashing" -> SLASHING
                "thunder" -> THUNDER
                else -> throw IllegalArgumentException("Unknown damage type: $value")
            }
        }
    }
}