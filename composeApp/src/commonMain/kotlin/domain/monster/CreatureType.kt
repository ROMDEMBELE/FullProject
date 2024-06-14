package domain.monster

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class CreatureType(val color: Color) {
    @SerialName("aberration")
    ABERRATION(Color(0xFF6A0DAD)), // Purple

    @SerialName("beast")
    BEAST(Color(0xFF00FF00)), // Green

    @SerialName("celestial")
    CELESTIAL(Color(0xFFFFD700)), // Gold

    @SerialName("construct")
    CONSTRUCT(Color(0xFFA9A9A9)), // Dark Gray

    @SerialName("dragon")
    DRAGON(Color(0xFFFF4500)), // Orange Red

    @SerialName("elemental")
    ELEMENTAL(Color(0xFF1E90FF)), // Dodger Blue

    @SerialName("fey")
    FEY(Color(0xFFFF69B4)), // Hot Pink

    @SerialName("fiend")
    FIEND(Color(0xFF8B0000)), // Dark Red

    @SerialName("giant")
    GIANT(Color(0xFFFF6347)), // Tomato

    @SerialName("humanoid")
    HUMANOID(Color(0xFF4682B4)), // Steel Blue

    @SerialName("monstrosity")
    MONSTROSITY(Color(0xFF2E8B57)), // Sea Green

    @SerialName("ooze")
    OOZE(Color(0xFF00CED1)), // Dark Turquoise

    @SerialName("plant")
    PLANT(Color(0xFF228B22)), // Forest Green

    @SerialName("undead")
    UNDEAD(Color(0xFF4B0082)); // Indigo
}