package domain.model.magicItem

data class MagicItem(
    val index: String,
    val isFavorite: Boolean,
    val name: String,
    val category: Category,
    val rarity: Rarity,
    val description: List<String>,
    val imageUrl: String?,
) {

    val hasAttunement: Boolean
        get() = description.any { it.contains("requires attunement", ignoreCase = true) }

    data class Category(
        val index: String,
        val name: String,
        val url: String,
    )
}