package data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AttackDcDto(
    @SerialName("dc_type")
    val typeOfDc: ReferenceDto,
    @SerialName("dc_value")
    val dcValue: Int,
    @SerialName("success_type")
    val successType: String
) {
    override fun toString(): String {
        return "Save DC $dcValue (${typeOfDc.name}) for $successType"
    }
}