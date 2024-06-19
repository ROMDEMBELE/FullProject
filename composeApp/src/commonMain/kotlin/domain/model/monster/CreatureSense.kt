package domain.model.monster

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.monster_senses_blind_sight
import org.dembeyo.shared.resources.monster_senses_dark_vision
import org.dembeyo.shared.resources.monster_senses_passive_perception
import org.dembeyo.shared.resources.monster_senses_tremor_sense
import org.dembeyo.shared.resources.monster_senses_true_sight
import org.jetbrains.compose.resources.StringResource

@Serializable
enum class CreatureSense(val fullName: StringResource) {
    @SerialName("darkvision")
    DARKVISION(Res.string.monster_senses_dark_vision),

    @SerialName("blindsight")
    BLINDSIGHT(Res.string.monster_senses_blind_sight),

    @SerialName("truesight")
    TRUESIGHT(Res.string.monster_senses_true_sight),

    @SerialName("tremorsense")
    TREMORSENSE(Res.string.monster_senses_tremor_sense),

    @SerialName("passive_perception")
    PASSIVE_PERCEPTION(Res.string.monster_senses_passive_perception)
}