package ui.magicItem.details

import domain.model.magicItem.ItemCategory
import domain.model.magicItem.ItemRarity


data class MagicItemDetailsUiState(
    val key: String? = null,
    val isFavorite: Boolean = false,
    val isMagical: Boolean = false,
    val name: String? = null,
    val description: String? = null,
    val cost: Double? = null,
    val weight: Double = 0.0,
    val requireAttunement: Boolean = false,
    val category: ItemCategory = ItemCategory.WONDROUS_ITEM,
    val rarity: ItemRarity = ItemRarity.NONE,
    val isReady: Boolean = false,
)