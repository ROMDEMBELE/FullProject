package domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Alignment {
    @SerialName("any alignment")
    AnyAlignment,

    @SerialName("any evil alignment")
    AnyEvilAlignment,

    @SerialName("any good alignment")
    AnyGoodAlignment,

    @SerialName("any non-lawful alignment")
    AnyNonLawfulAlignment,

    @SerialName("any non-good alignment")
    AnyNonGoodAlignment,

    @SerialName("any chaotic alignment")
    AnyChaoticAlignment,

    @SerialName("unaligned")
    Unaligned,

    @SerialName("chaotic evil")
    ChaoticEvil,

    @SerialName("chaotic good")
    ChaoticGood,

    @SerialName("chaotic neutral")
    ChaoticNeutral,

    @SerialName("lawful evil")
    LawfulEvil,

    @SerialName("lawful good")
    LawfulGood,

    @SerialName("lawful neutral")
    LawfulNeutral,

    @SerialName("neutral")
    Neutral,

    @SerialName("neutral evil")
    NeutralEvil,

    @SerialName("neutral good")
    NeutralGood;

    companion object {
        private val regexMatches = listOf(
            Regex("any alignment|all alignments", RegexOption.IGNORE_CASE) to AnyAlignment,
            Regex("any evil alignment|any evil", RegexOption.IGNORE_CASE) to AnyEvilAlignment,
            Regex("any good alignment|any good|good", RegexOption.IGNORE_CASE) to AnyGoodAlignment,
            Regex("any non-lawful|any non-lawful alignment|non-lawful alignments|non-lawful", RegexOption.IGNORE_CASE) to AnyNonLawfulAlignment,
            Regex("any non-good alignment|non-good alignments|non-good", RegexOption.IGNORE_CASE) to AnyNonGoodAlignment,
            Regex("any chaotic alignment|chaotic alignments|any chaotic", RegexOption.IGNORE_CASE) to AnyChaoticAlignment,
            Regex("unaligned|no alignment|none", RegexOption.IGNORE_CASE) to Unaligned,
            Regex("chaotic evil|ce|chaos evil", RegexOption.IGNORE_CASE) to ChaoticEvil,
            Regex("chaotic good|cg|chaos good", RegexOption.IGNORE_CASE) to ChaoticGood,
            Regex("chaotic neutral|cn|chaos neutral", RegexOption.IGNORE_CASE) to ChaoticNeutral,
            Regex("lawful evil|le|law evil", RegexOption.IGNORE_CASE) to LawfulEvil,
            Regex("lawful good|lg|law good", RegexOption.IGNORE_CASE) to LawfulGood,
            Regex("lawful neutral|ln|law neutral", RegexOption.IGNORE_CASE) to LawfulNeutral,
            Regex("neutral|true neutral|tn|balanced", RegexOption.IGNORE_CASE) to Neutral,
            Regex("neutral evil|ne|neutral+evil", RegexOption.IGNORE_CASE) to NeutralEvil,
            Regex("neutral good|ng|neutral+good", RegexOption.IGNORE_CASE) to NeutralGood,
            Regex("any", RegexOption.IGNORE_CASE) to Unaligned // Specific case for "any"
        )

        fun fromString(string: String): Alignment {
            val normalized = string.trim()
            regexMatches.forEach { (pattern, alignment) ->
                if (pattern.containsMatchIn(normalized)) {
                    return alignment
                }
            }

            println("Warning: Unknown alignment string '$string'. Returning Unaligned.")
            return Unaligned // Default fallback
        }
    }
}