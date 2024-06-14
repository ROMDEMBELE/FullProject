package data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface OptionContainer<out T> {
    val choose: Int
    val from: OptionSet<T>

    @Serializable
    data class OptionSet<out T>(
        @SerialName("option_set_type")
        val optionSetType: String,
        val options: List<T>
    )
}