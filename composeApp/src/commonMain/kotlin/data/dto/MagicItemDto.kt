package data.dto

import domain.model.magicItem.Rarity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MagicItemDto(
    val index: String,
    val name: String,
    @SerialName("equipment_category")
    val category: ReferenceDto,
    val rarity: Rarity,
    val variant: Boolean,
    val desc: List<String>,
    val url: String
) {
}


