package data.dto

import kotlinx.serialization.Serializable

@Serializable
data class SpellDto(
    val index: String,
    val name: String,
    val url: String,
    val level: Int,
    val desc: List<String>? = null,
    val higher_level: List<String>? = null,
    val range: String? = null,
    val components: List<String>? = null,
    val material: String? = null,
    val ritual: Boolean? = null,
    val duration: String? = null,
    val concentration: Boolean? = null,
    val casting_time: String? = null,
    val attack_type: String? = null,
    val damage: DamageDto? = null,
    val dc: DcDto? = null,
    val area_of_effect: AreaOfEffectDto? = null,
    val school: ReferenceDto? = null,
    val classes: List<ReferenceDto>? = null,
    val subclasses: List<ReferenceDto>? = null,
) {

    @Serializable
    data class DamageDto(
        val damage_type: ReferenceDto,
        val damage_at_slot_level: Map<Int, String>? = null,
        val damage_at_character_level: Map<Int, String>? = null
    )

    @Serializable
    data class DcDto(
        val dc_type: ReferenceDto,
        val dc_success: String
    )

    @Serializable
    data class AreaOfEffectDto(
        val type: String,
        val size: Int
    )
}