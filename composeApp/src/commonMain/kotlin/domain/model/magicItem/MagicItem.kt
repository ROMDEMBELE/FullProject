package domain.model.magicItem

data class MagicItem(
    val index: String,
    val isFavorite: Boolean,
    val name: String,
    val category: String,
    val rarity: Rarity,
    var description: List<String>
)