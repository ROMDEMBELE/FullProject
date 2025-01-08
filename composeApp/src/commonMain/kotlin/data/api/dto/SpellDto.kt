package data.api.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpellDto(
    @SerialName("url")
    val url: String,
    @SerialName("document")
    val document: String,
    @SerialName("key")
    val key: String,
    @SerialName("school")
    val school: String,
    @SerialName("classes")
    val classes: List<String>,
    @SerialName("range_unit")
    val rangeUnit: String,
    @SerialName("shape_size_unit")
    val shapeSizeUnit: String,
    @SerialName("name")
    val name: String,
    @SerialName("desc")
    val desc: String,
    @SerialName("level")
    val level: Int,
    @SerialName("higher_level")
    val higherLevel: String?,
    @SerialName("target_type")
    val targetType: String,
    @SerialName("range_text")
    val rangeText: String,
    @SerialName("range")
    val range: Double,
    @SerialName("ritual")
    val ritual: Boolean,
    @SerialName("casting_time")
    val castingTime: String,
    @SerialName("reaction_condition")
    val reactionCondition: String?,
    @SerialName("verbal")
    val verbal: Boolean,
    @SerialName("somatic")
    val somatic: Boolean,
    @SerialName("material")
    val material: Boolean,
    @SerialName("material_specified")
    val materialSpecified: String?,
    @SerialName("material_cost")
    val materialCost: Double?,
    @SerialName("material_consumed")
    val materialConsumed: Boolean,
    @SerialName("target_count")
    val targetCount: Int,
    @SerialName("saving_throw_ability")
    val savingThrowAbility: String?,
    @SerialName("attack_roll")
    val attackRoll: Boolean,
    @SerialName("damage_roll")
    val damageRoll: String?,
    @SerialName("damage_types")
    val damageTypes: List<String>,
    @SerialName("duration")
    val duration: String,
    @SerialName("shape_type")
    val shapeType: String?,
    @SerialName("shape_size")
    val shapeSize: Double?,
    @SerialName("concentration")
    val concentration: Boolean,
    @SerialName("casting_options")
    val castingOptions: List<CastingOptionDto>
) {

    @Serializable
    data class CastingOptionDto(
        @SerialName("type")
        val type: String,
        @SerialName("damage_roll")
        val damageRoll: String?,
        @SerialName("target_count")
        val targetCount: Int?,
        @SerialName("duration")
        val duration: String?,
        @SerialName("range")
        val range: String?,
        @SerialName("concentration")
        val concentration: Boolean?,
        @SerialName("shape_size")
        val shapeSize: String?
    )
}

