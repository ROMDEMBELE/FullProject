package domain.model.magicItem

enum class ItemCategory(
    val url: String
) {
    RING("https://api.open5e.com/v2/itemcategories/ring/"),
    ADVENTURING_GEAR("https://api.open5e.com/v2/itemcategories/adventuring-gear/"),
    AMMUNITION("https://api.open5e.com/v2/itemcategories/ammunition/"),
    ARMOR("https://api.open5e.com/v2/itemcategories/armor/"),
    ART("https://api.open5e.com/v2/itemcategories/art/"),
    DRAWN_VEHICLE("https://api.open5e.com/v2/itemcategories/drawn-vehicle/"),
    GEM("https://api.open5e.com/v2/itemcategories/gem/"),
    JEWELRY("https://api.open5e.com/v2/itemcategories/jewelry/"),
    POISON("https://api.open5e.com/v2/itemcategories/poison/"),
    POTION("https://api.open5e.com/v2/itemcategories/potion/"),
    ROD("https://api.open5e.com/v2/itemcategories/rod/"),
    SCROLL("https://api.open5e.com/v2/itemcategories/scroll/"),
    SHIELD("https://api.open5e.com/v2/itemcategories/shield/"),
    STAFF("https://api.open5e.com/v2/itemcategories/staff/"),
    TOOLS("https://api.open5e.com/v2/itemcategories/tools/"),
    TRADE_GOOD("https://api.open5e.com/v2/itemcategories/trade-good/"),
    WAND("https://api.open5e.com/v2/itemcategories/wand/"),
    WATERBORNE_VEHICLE("https://api.open5e.com/v2/itemcategories/waterborne-vehicle/"),
    WEAPON("https://api.open5e.com/v2/itemcategories/weapon/"),
    WONDROUS_ITEM("https://api.open5e.com/v2/itemcategories/wondrous-item/");

    companion object {
        fun fromUrl(url: String): ItemCategory {
            return entries.find { it.url == url }
                ?: throw IllegalArgumentException("Unknown ItemCategory URL: $url")
        }

    }
}