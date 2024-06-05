package domain

import androidx.compose.ui.graphics.Color

enum class MagicSchool(val index: String, val displayName: String, val color: Color) {
    ABJURATION("abjuration", "Abjuration", Color(0xFFAEDFF7)), // Pastel Blue
    CONJURATION("conjuration", "Conjuration", Color(0xFFD8BFD8)), // Pastel Purple
    DIVINATION("divination", "Divination", Color(0xFFFFFFFF)), // White
    ENCHANTMENT("enchantment", "Enchantment", Color(0xFFFFE4E1)), // Pastel Pink
    EVOCATION("evocation", "Evocation", Color(0xFFFFA07A)), // Pastel Red
    ILLUSION("illusion", "Illusion", Color(0xFFE0E0E0)), // Pastel Silver
    NECROMANCY("necromancy", "Necromancy", Color(0xFFA9A9A9)), // Pastel Black (Grey)
    TRANSMUTATION("transmutation", "Transmutation", Color(0xFF90EE90)); // Pastel Green

    companion object {
        fun valueOfOrNull(name: String): MagicSchool? = entries.find { it.name == name }

        fun fromIndex(index: String): MagicSchool? {
            return entries.find { it.index == index }
        }
    }
}