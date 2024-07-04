package data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MagicItemDto(
    val index: String,
    val name: String,
    @SerialName("equipment_category")
    val category: ReferenceDto,
    val rarity: RarityDto,
    val variant: Boolean,
    val desc: List<String>,
    val url: String
) {
    @Serializable
    data class RarityDto(
        val name: String
    )
}


