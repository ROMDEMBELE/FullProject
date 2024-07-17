package domain.model.magicItem

import androidx.compose.ui.graphics.Color

enum class Rarity(val text: String, val color: Color) {
    COMMON("Common", Color(0xFFB69470)),
    UNCOMMON("Uncommon", Color(0xFFCD8E4B)),
    RARE("Rare", Color(0xFFEABA25)),
    VERY_RARE("Very Rare", Color(0xFFEF9315)),
    LEGENDARY("Legendary", Color(0xFFEF5B15)),
    ARTIFACT("Artifact", Color(0xFFD61070)),
    VARIES("Varies", Color(0xFFD7C1CC));

    companion object {
        fun fromText(text: String): Rarity = entries.firstOrNull { it.text == text }
            ?: throw NoSuchElementException("Invalid rarity $text")
    }
}