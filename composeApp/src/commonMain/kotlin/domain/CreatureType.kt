package domain

import androidx.compose.ui.graphics.Color

enum class CreatureType(val description: String, val color: Color) {
    ABERRATION("aberration", Color(0xFF6A0DAD)), // Purple
    BEAST("beast", Color(0xFF00FF00)), // Green
    CELESTIAL("celestial", Color(0xFFFFD700)), // Gold
    CONSTRUCT("construct", Color(0xFFA9A9A9)), // Dark Gray
    DRAGON("dragon", Color(0xFFFF4500)), // Orange Red
    ELEMENTAL("elemental", Color(0xFF1E90FF)), // Dodger Blue
    FEY("fey", Color(0xFFFF69B4)), // Hot Pink
    FIEND("fiend", Color(0xFF8B0000)), // Dark Red
    GIANT("giant", Color(0xFFFF6347)), // Tomato
    HUMANOID("humanoid", Color(0xFF4682B4)), // Steel Blue
    MONSTROSITY("monstrosity", Color(0xFF2E8B57)), // Sea Green
    OOZE("ooze", Color(0xFF00CED1)), // Dark Turquoise
    PLANT("plant", Color(0xFF228B22)), // Forest Green
    UNDEAD("undead", Color(0xFF4B0082)); // Indigo

    companion object {
        fun fromString(description: String): CreatureType {
            return entries.find { it.description.equals(description, ignoreCase = true) }
                ?: throw IllegalArgumentException("Unknown type: $description")
        }
    }
}