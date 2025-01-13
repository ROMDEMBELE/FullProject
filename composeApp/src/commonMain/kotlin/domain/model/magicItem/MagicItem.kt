package domain.model.magicItem

data class MagicItem(
    val key: String,
    val isFavorite: Boolean,
    val isMagical: Boolean,
    val name: String,
    val description: String,
    val cost: Double? = null,
    val weight: Double,
    val requireAttunement: Boolean,
    val category: ItemCategory,
    val rarity: ItemRarity,
)