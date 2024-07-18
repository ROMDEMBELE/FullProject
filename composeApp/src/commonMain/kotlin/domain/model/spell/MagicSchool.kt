package domain.model.spell

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.Serializable
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.abjuration
import org.dembeyo.shared.resources.conjuration
import org.dembeyo.shared.resources.divination
import org.dembeyo.shared.resources.enchantment
import org.dembeyo.shared.resources.evocation
import org.dembeyo.shared.resources.illusion
import org.dembeyo.shared.resources.necromancy
import org.dembeyo.shared.resources.transmutation
import org.jetbrains.compose.resources.StringResource

@Serializable
enum class MagicSchool(val index: String, val stringRes: StringResource, val color: Color) {
    ABJURATION("abjuration", Res.string.abjuration, Color(0xFFAEDFF7)), // Pastel Blue
    CONJURATION("conjuration", Res.string.conjuration, Color(0xFFD8BFD8)), // Pastel Purple
    DIVINATION("divination", Res.string.divination, Color(0xFFFFFFFF)), // White
    ENCHANTMENT("enchantment", Res.string.enchantment, Color(0xFFFFE4E1)), // Pastel Pink
    EVOCATION("evocation", Res.string.evocation, Color(0xFFFFA07A)), // Pastel Red
    ILLUSION("illusion", Res.string.illusion, Color(0xFFE0E0E0)), // Pastel Silver
    NECROMANCY("necromancy", Res.string.necromancy, Color(0xFFA9A9A9)), // Pastel Black (Grey)
    TRANSMUTATION("transmutation", Res.string.transmutation, Color(0xFF90EE90)); // Pastel Green

    companion object {
        fun fromIndex(index: String) = entries.find { it.index == index }
    }
}