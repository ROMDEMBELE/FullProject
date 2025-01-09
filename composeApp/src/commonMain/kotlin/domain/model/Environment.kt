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

    @SerialName("feywild")
    FEYWILD,

    @SerialName("jungle")
    JUNGLE,

    @SerialName("cave")
    CAVE,

    @SerialName("laboratory")
    LABORATORY,

    @SerialName("underwater")
    UNDERWATER,

    @SerialName("ruins")
    RUINS,

    @SerialName("shadowfell")
    SHADOWFELL,

    @SerialName("hell")
    HELL,

    @SerialName("lake")
    LAKE,

    @SerialName("sewer")
    SEWER,

    @SerialName("abyss")
    ABYSS,

    @SerialName("astral")
    ASTRAL,

    @SerialName("elysium")
    ELYSIUM,

    @SerialName("ethereal")
    ETHEREAL,

    @SerialName("material_plane")
    MATERIAL_PLANE,

    @SerialName("plane_of_air")
    PLANE_OF_AIR,

    @SerialName("plane_of_earth")
    PLANE_OF_EARTH,

    @SerialName("plane_of_fire")
    PLANE_OF_FIRE,

    @SerialName("plane_of_water")
    PLANE_OF_WATER,

    @SerialName("temple")
    TEMPLE,

    @SerialName("badlands")
    BADLANDS,

    @SerialName("tomb")
    TOMB,

    @SerialName("unknown")
    UNKNOWN;

    companion object {
        private val regexMatches = listOf(
            Regex("arctic|artic|tundra", RegexOption.IGNORE_CASE) to ARCTIC,
            Regex("coast|coastal|shore", RegexOption.IGNORE_CASE) to COASTAL,
            Regex("desert|dune|sands", RegexOption.IGNORE_CASE) to DESERT,
            Regex("forest|woods|woodland", RegexOption.IGNORE_CASE) to FOREST,
            Regex("grassland|plains|savanna", RegexOption.IGNORE_CASE) to GRASSLAND,
            Regex("hill|hills|knoll", RegexOption.IGNORE_CASE) to HILL,
            Regex("mountain|peak|highland", RegexOption.IGNORE_CASE) to MOUNTAIN,
            Regex("swamp|marsh|bog", RegexOption.IGNORE_CASE) to SWAMP,
            Regex("underdark|underworld", RegexOption.IGNORE_CASE) to UNDERDARK,
            Regex("urban|city|town", RegexOption.IGNORE_CASE) to URBAN,
            Regex("feywild|faerie", RegexOption.IGNORE_CASE) to FEYWILD,
            Regex("jungle|rainforest", RegexOption.IGNORE_CASE) to JUNGLE,
            Regex("cave|cavern", RegexOption.IGNORE_CASE) to CAVE,
            Regex("laboratory|lab|research center", RegexOption.IGNORE_CASE) to LABORATORY,
            Regex("underwater|ocean|sea", RegexOption.IGNORE_CASE) to UNDERWATER,
            Regex("ruins|remains|wreckage", RegexOption.IGNORE_CASE) to RUINS,
            Regex("shadowfell|shadows|dark realm", RegexOption.IGNORE_CASE) to SHADOWFELL,
            Regex("hell|inferno|abyssal", RegexOption.IGNORE_CASE) to HELL,
            Regex("lake|pond|lagoon", RegexOption.IGNORE_CASE) to LAKE,
            Regex("sewer|drainage", RegexOption.IGNORE_CASE) to SEWER,
            Regex("abyss", RegexOption.IGNORE_CASE) to ABYSS,
            Regex("astral plane|astral", RegexOption.IGNORE_CASE) to ASTRAL,
            Regex("elysium|paradise|heaven", RegexOption.IGNORE_CASE) to ELYSIUM,
            Regex("ethereal plane|ethereal", RegexOption.IGNORE_CASE) to ETHEREAL,
            Regex("material plane|material", RegexOption.IGNORE_CASE) to MATERIAL_PLANE,
            Regex("plane of air|air plane|sky", RegexOption.IGNORE_CASE) to PLANE_OF_AIR,
            Regex("plane of earth|earth plane", RegexOption.IGNORE_CASE) to PLANE_OF_EARTH,
            Regex("plane of fire|fire plane", RegexOption.IGNORE_CASE) to PLANE_OF_FIRE,
            Regex(
                "plane of water|water plane|sea plane",
                RegexOption.IGNORE_CASE
            ) to PLANE_OF_WATER,
            Regex("temple|shrine|sanctuary", RegexOption.IGNORE_CASE) to TEMPLE,
            Regex("badlands|wasteland|desolate", RegexOption.IGNORE_CASE) to BADLANDS,
            Regex("tomb|crypt|grave", RegexOption.IGNORE_CASE) to TOMB
        )

        fun fromString(value: String): Environment {
            val normalized = value.trim().lowercase()

            // Regex matching
            regexMatches.forEach { (pattern, environment) ->
                if (pattern.containsMatchIn(normalized)) return environment
            }

            // Fallback
            println("Warning: Unmapped environment string '$value'. Returning UNKNOWN.")
            return UNKNOWN
        }
    }
}