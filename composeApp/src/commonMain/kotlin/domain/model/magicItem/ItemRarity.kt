package domain.model.magicItem

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ItemRarity(val key: String, val url: String?) {
    @SerialName("None")
    NONE("none", null),

    @SerialName("Common")
    COMMON("common", "https://api.open5e.com/v2/itemrarities/common/"),

    @SerialName("Uncommon")
    UNCOMMON("uncommon", "https://api.open5e.com/v2/itemrarities/uncommon/"),

    @SerialName("Rare")
    RARE("rare", "https://api.open5e.com/v2/itemrarities/rare/"),

    @SerialName("Very Rare")
    VERY_RARE("very-rare", "https://api.open5e.com/v2/itemrarities/very-rare/"),

    @SerialName("Legendary")
    LEGENDARY("legendary", "https://api.open5e.com/v2/itemrarities/legendary/"),

    @SerialName("Artifact")
    ARTIFACT("artifact", "https://api.open5e.com/v2/itemrarities/artifact/");

    companion object {

        fun fromUrl(url: String? = null): ItemRarity {
            return entries.find { it.url == url } ?: throw IllegalArgumentException("Unknown ItemRarity URL: $url")
        }

    }

}