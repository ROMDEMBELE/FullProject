package domain.model.monster

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.burrow
import org.dembeyo.shared.resources.climb
import org.dembeyo.shared.resources.dig
import org.dembeyo.shared.resources.fly
import org.dembeyo.shared.resources.ghost
import org.dembeyo.shared.resources.hover
import org.dembeyo.shared.resources.swim
import org.dembeyo.shared.resources.walk
import org.dembeyo.shared.resources.wing
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

@Serializable
enum class CreatureMovement(val stringRes: StringResource, val icon: DrawableResource) {
    @SerialName("burrow")
    BURROW(Res.string.burrow, Res.drawable.dig),

    @SerialName("hover")
    HOVER(Res.string.hover, Res.drawable.ghost),

    @SerialName("climb")
    CLIMB(Res.string.climb, Res.drawable.climb),

    @SerialName("walk")
    WALK(Res.string.walk, Res.drawable.walk),

    @SerialName("fly")
    FLY(Res.string.fly, Res.drawable.wing),

    @SerialName("swim")
    SWIM(Res.string.swim, Res.drawable.swim);
}
