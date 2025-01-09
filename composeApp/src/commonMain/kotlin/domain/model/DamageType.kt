package domain.model

import kotlinx.serialization.SerialName

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
        fun fromUrl(url: String): DamageType {
            return when {
                url.contains("acid", ignoreCase = true) -> ACID
                url.contains("bludgeoning", ignoreCase = true) -> BLUDGEONING
                url.contains("cold", ignoreCase = true) -> COLD
                url.contains("fire", ignoreCase = true) -> FIRE
                url.contains("force", ignoreCase = true) -> FORCE
                url.contains("lightning", ignoreCase = true) -> LIGHTNING
                url.contains("necrotic", ignoreCase = true) -> NECROTIC
                url.contains("piercing", ignoreCase = true) -> PIERCING
                url.contains("poison", ignoreCase = true) -> POISON
                url.contains("psychic", ignoreCase = true) -> PSYCHIC
                url.contains("radiant", ignoreCase = true) -> RADIANT
                url.contains("slashing", ignoreCase = true) -> SLASHING
                url.contains("thunder", ignoreCase = true) -> THUNDER
                else -> throw IllegalArgumentException("Unknown damage type: $url")
            }
        }

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