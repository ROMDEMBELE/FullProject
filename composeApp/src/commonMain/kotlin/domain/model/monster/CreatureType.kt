package domain.model.monster

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.aberration
import org.dembeyo.shared.resources.beast
import org.dembeyo.shared.resources.celestial
import org.dembeyo.shared.resources.construct
import org.dembeyo.shared.resources.dragon
import org.dembeyo.shared.resources.elemental
import org.dembeyo.shared.resources.fey
import org.dembeyo.shared.resources.fiend
import org.dembeyo.shared.resources.giant
import org.dembeyo.shared.resources.humanoid
import org.dembeyo.shared.resources.monstrosity
import org.dembeyo.shared.resources.ooze
import org.dembeyo.shared.resources.plant
import org.dembeyo.shared.resources.undead
import org.jetbrains.compose.resources.StringResource

@Serializable
enum class CreatureType(val color: Color, val stringRes: StringResource) {
    @SerialName("aberration")
    ABERRATION(Color(0xFF6A0DAD), Res.string.aberration), // Purple

    @SerialName("beast")
    BEAST(Color(0xFF00FF00), Res.string.beast), // Green

    @SerialName("celestial")
    CELESTIAL(Color(0xFFFFD700), Res.string.celestial), // Gold

    @SerialName("construct")
    CONSTRUCT(Color(0xFFA9A9A9), Res.string.construct), // Dark Gray

    @SerialName("dragon")
    DRAGON(Color(0xFFFF4500), Res.string.dragon), // Orange Red

    @SerialName("elemental")
    ELEMENTAL(Color(0xFF1E90FF), Res.string.elemental), // Dodger Blue

    @SerialName("fey")
    FEY(Color(0xFFFF69B4), Res.string.fey), // Hot Pink

    @SerialName("fiend")
    FIEND(Color(0xFF8B0000), Res.string.fiend), // Dark Red

    @SerialName("giant")
    GIANT(Color(0xFFFF6347), Res.string.giant), // Tomato

    @SerialName("humanoid")
    HUMANOID(Color(0xFF4682B4), Res.string.humanoid), // Steel Blue

    @SerialName("monstrosity")
    MONSTROSITY(Color(0xFF2E8B57), Res.string.monstrosity), // Sea Green

    @SerialName("ooze")
    OOZE(Color(0xFF00CED1), Res.string.ooze), // Dark Turquoise

    @SerialName("plant")
    PLANT(Color(0xFF228B22), Res.string.plant), // Forest Green

    @SerialName("undead")
    UNDEAD(Color(0xFF4B0082), Res.string.undead); // Indigo
}
