package domain.model.monster

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class CreatureType(val color: Color, val fullName: String) {
    @SerialName("aberration")
    ABERRATION(Color(0xFF6A0DAD), "aberration"), // Purple

    @SerialName("beast")
    BEAST(Color(0xFF00FF00), "beast"), // Green

    @SerialName("celestial")
    CELESTIAL(Color(0xFFFFD700), "celestial"), // Gold

    @SerialName("construct")
    CONSTRUCT(Color(0xFFA9A9A9), "construct"), // Dark Gray

    @SerialName("dragon")
    DRAGON(Color(0xFFFF4500), "dragon"), // Orange Red

    @SerialName("elemental")
    ELEMENTAL(Color(0xFF1E90FF), "elemental"), // Dodger Blue

    @SerialName("fey")
    FEY(Color(0xFFFF69B4), "fey"), // Hot Pink

    @SerialName("fiend")
    FIEND(Color(0xFF8B0000), "fiend"), // Dark Red

    @SerialName("giant")
    GIANT(Color(0xFFFF6347), "giant"), // Tomato

    @SerialName("humanoid")
    HUMANOID(Color(0xFF4682B4), "humanoid"), // Steel Blue

    @SerialName("monstrosity")
    MONSTROSITY(Color(0xFF2E8B57), "monstrosity"), // Sea Green

    @SerialName("ooze")
    OOZE(Color(0xFF00CED1), "ooze"), // Dark Turquoise

    @SerialName("plant")
    PLANT(Color(0xFF228B22), "plant"), // Forest Green

    @SerialName("undead")
    UNDEAD(Color(0xFF4B0082), "undead"); // Indigo
}
