package domain

import kotlinx.serialization.Serializable

@Serializable
data class Spell(
    val index: String,
    val name: String,
    val level: Level,
    var isFavorite: Boolean = false,
    val text: String? = null,
    val range: String? = null,
    val components: String? = null,
    val material: String? = null,
    val ritual: Boolean? = null,
    val duration: String? = null,
    val concentration: Boolean? = null,
    val casting_time: String? = null,
    val attack_type: String? = null,
    val damageType: String? = null,
    val damageSlot: Map<Level, String> = emptyMap(),
    val save: String? = null,
    val school: MagicSchool? = null,
)