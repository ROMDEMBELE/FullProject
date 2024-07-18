package domain.model.monster

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.gargantuan
import org.dembeyo.shared.resources.huge
import org.dembeyo.shared.resources.large
import org.dembeyo.shared.resources.medium
import org.dembeyo.shared.resources.small
import org.dembeyo.shared.resources.tiny
import org.jetbrains.compose.resources.StringResource

@Serializable
enum class CreatureSize(val stringRes: StringResource) {
    @SerialName("Tiny")
    TINY(Res.string.tiny),

    @SerialName("Small")
    SMALL(Res.string.small),

    @SerialName("Medium")
    MEDIUM(Res.string.medium),

    @SerialName("Large")
    LARGE(Res.string.large),

    @SerialName("Huge")
    HUGE(Res.string.huge),

    @SerialName("Gargantuan")
    GARGANTUAN(Res.string.gargantuan);
}
