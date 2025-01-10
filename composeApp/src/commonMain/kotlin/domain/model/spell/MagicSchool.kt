package domain.model.spell

import kotlinx.serialization.Serializable

@Serializable
enum class MagicSchool(val index: String) {
    ABJURATION("abjuration"),
    CONJURATION("conjuration"),
    DIVINATION("divination"),
    ENCHANTMENT("enchantment"),
    EVOCATION("evocation"),
    ILLUSION("illusion"),
    NECROMANCY("necromancy"),
    TRANSMUTATION("transmutation");

    companion object {
        fun fromUrl(url: String): MagicSchool =
            entries.firstOrNull { url.contains(it.index, ignoreCase = true) }
                ?: throw IllegalArgumentException("No MagicSchool found for $url")
    }
}