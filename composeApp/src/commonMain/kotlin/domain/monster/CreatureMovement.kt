package domain.monster

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.climb
import org.dembeyo.shared.resources.ghost
import org.dembeyo.shared.resources.swim
import org.dembeyo.shared.resources.walk
import org.dembeyo.shared.resources.wing
import org.jetbrains.compose.resources.DrawableResource

@Serializable
enum class CreatureMovement(val fullName: String, val icon: DrawableResource) {
    @SerialName("hover")
    HOVER("hover", Res.drawable.ghost),

    @SerialName("climb")
    CLIMB("climb", Res.drawable.climb),

    @SerialName("walk")
    WALK("walk", Res.drawable.walk),

    @SerialName("fly")
    FLY("fly", Res.drawable.wing),

    @SerialName("swim")
    SWIM("swim", Res.drawable.swim);
}
