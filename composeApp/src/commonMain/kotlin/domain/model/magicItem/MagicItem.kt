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
) {
    override fun equals(other: Any?): Boolean {
        return other is MagicItem && (other.key == key || other.name == name) && other.isFavorite == isFavorite
    }

    override fun hashCode(): Int {
        var result = key.hashCode()
        result = 31 * result + isFavorite.hashCode()
        return result
    }
}