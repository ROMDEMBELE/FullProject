package data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpellDto(
    val index: String,
    val name: String,
    val url: String,
    val level: Int,
    val desc: List<String>,
    @SerialName("higher_level")
    val higherLevel: List<String>,
    val range: String,
    val components: List<String>,
    val material: String? = null,
    val ritual: Boolean,
    val duration: String,
    val concentration: Boolean,
    @SerialName("casting_time")
    val castingTime: String,
    @SerialName("attack_type")
    val attackType: String? = null,
    val damage: SpellDamageDto? = null,
    val dc: SpellDcDto? = null,
    @SerialName("area_of_effect")
    val areaOfEffect: AreaOfEffectDto? = null,
    val school: ReferenceDto,
    val classes: List<ReferenceDto>,
    val subclasses: List<ReferenceDto>,
) {

    @Serializable
    data class SpellDcDto(
        @SerialName("dc_type")
        val dcType: ReferenceDto,
        @SerialName("dc_success")
        val dcSuccess: String
    )

    @Serializable
    data class SpellDamageDto(
        @SerialName("damage_type")
        val damageType: ReferenceDto,
        @SerialName("damage_at_slot_level")
        val damageAtSlotLevel: Map<Int, String>? = null,
        @SerialName("damage_at_character_level")
        val damageAtCharacterLevel: Map<Int, String>? = null
    )

    @Serializable
    data class AreaOfEffectDto(
        val type: String,
        val size: Int
    )
}