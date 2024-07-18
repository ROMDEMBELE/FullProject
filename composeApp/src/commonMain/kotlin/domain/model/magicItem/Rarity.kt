package domain.model.magicItem

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.artifact
import org.dembeyo.shared.resources.common
import org.dembeyo.shared.resources.legendary
import org.dembeyo.shared.resources.rare
import org.dembeyo.shared.resources.uncommon
import org.dembeyo.shared.resources.varies
import org.dembeyo.shared.resources.very_rare
import org.jetbrains.compose.resources.StringResource

@Serializable
enum class Rarity(val stringRes: StringResource, val color: Color) {

    @SerialName("Common")
    COMMON(Res.string.common, Color(0xFFB69470)),

    @SerialName("Uncommon")
    UNCOMMON(Res.string.uncommon, Color(0xFFCD8E4B)),

    @SerialName("Rare")
    RARE(Res.string.rare, Color(0xFFEABA25)),

    @SerialName("Very Rare")
    VERY_RARE(Res.string.very_rare, Color(0xFFEF9315)),

    @SerialName("Legendary")
    LEGENDARY(Res.string.legendary, Color(0xFFEF5B15)),

    @SerialName("Artifact")
    ARTIFACT(Res.string.artifact, Color(0xFFD61070)),

    @SerialName("Varies")
    VARIES(Res.string.varies, Color(0xFFD7C1CC));

}