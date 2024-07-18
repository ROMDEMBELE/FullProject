package domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.any_alignment
import org.dembeyo.shared.resources.any_evil_alignment
import org.dembeyo.shared.resources.chaotic_evil
import org.dembeyo.shared.resources.chaotic_good
import org.dembeyo.shared.resources.chaotic_neutral
import org.dembeyo.shared.resources.lawful_evil
import org.dembeyo.shared.resources.lawful_good
import org.dembeyo.shared.resources.lawful_neutral
import org.dembeyo.shared.resources.neutral
import org.dembeyo.shared.resources.neutral_evil
import org.dembeyo.shared.resources.neutral_good
import org.dembeyo.shared.resources.unaligned
import org.jetbrains.compose.resources.StringResource

@Serializable
enum class Alignment(val stringRes: StringResource) {
    @SerialName("any alignment")
    ANY_ALIGNMENT(Res.string.any_alignment),

    @SerialName("any evil alignment")
    ANY_EVIL_ALIGNMENT(Res.string.any_evil_alignment),

    @SerialName("unaligned")
    UNALIGNED(Res.string.unaligned),

    @SerialName("chaotic evil")
    CHAOTIC_EVIL(Res.string.chaotic_evil),

    @SerialName("chaotic good")
    CHAOTIC_GOOD(Res.string.chaotic_good),

    @SerialName("chaotic neutral")
    CHAOTIC_NEUTRAL(Res.string.chaotic_neutral),

    @SerialName("lawful evil")
    LAWFUL_EVIL(Res.string.lawful_evil),

    @SerialName("lawful good")
    LAWFUL_GOOD(Res.string.lawful_good),

    @SerialName("lawful neutral")
    LAWFUL_NEUTRAL(Res.string.lawful_neutral),

    @SerialName("neutral")
    NEUTRAL(Res.string.neutral),

    @SerialName("neutral evil")
    NEUTRAL_EVIL(Res.string.neutral_evil),

    @SerialName("neutral good")
    NEUTRAL_GOOD(Res.string.neutral_good);
}
