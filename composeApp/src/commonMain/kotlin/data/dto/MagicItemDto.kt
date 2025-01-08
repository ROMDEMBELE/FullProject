package data.dto

import domain.model.magicItem.Rarity
import kotlinx.serialization.Serializable

@Serializable
data class MagicItemDto(
    val index: String,
    val name: String,
    val rarity: Rarity,
    val variant: Boolean,
    val desc: List<String>,
    val url: String
) {
}


