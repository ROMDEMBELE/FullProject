package domain

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.damage_acid
import org.dembeyo.shared.resources.damage_bludgeoning
import org.dembeyo.shared.resources.damage_cold
import org.dembeyo.shared.resources.damage_fire
import org.dembeyo.shared.resources.damage_force
import org.dembeyo.shared.resources.damage_lightning
import org.dembeyo.shared.resources.damage_necrotic
import org.dembeyo.shared.resources.damage_piercing
import org.dembeyo.shared.resources.damage_poison
import org.dembeyo.shared.resources.damage_psychic
import org.dembeyo.shared.resources.damage_radiant
import org.dembeyo.shared.resources.damage_slash
import org.dembeyo.shared.resources.damage_thunder
import org.jetbrains.compose.resources.DrawableResource

@Serializable
enum class DamageType(val index: String, val color: Color, val icon: DrawableResource) {
    @SerialName("acid")
    ACID("acid", Color(0, 255, 0), Res.drawable.damage_acid),  // Vert

    @SerialName("bludgeoning")
    BLUDGEONING("bludgeoning", Color(139, 69, 19), Res.drawable.damage_bludgeoning),  // Marron

    @SerialName("cold")
    COLD("cold", Color(173, 216, 230), Res.drawable.damage_cold),  // Bleu clair

    @SerialName("fire")
    FIRE("fire", Color(255, 69, 0), Res.drawable.damage_fire),  // Orange/rouge

    @SerialName("force")
    FORCE("force", Color(148, 0, 211), Res.drawable.damage_force),  // Violet foncé

    @SerialName("lightning")
    LIGHTNING("lightning", Color(255, 255, 0), Res.drawable.damage_lightning),  // Jaune

    @SerialName("necrotic")
    NECROTIC("necrotic", Color(171, 67, 231), Res.drawable.damage_necrotic),  // Indigo

    @SerialName("piercing")
    PIERCING("piercing", Color(165, 42, 42), Res.drawable.damage_piercing),  // Marron/rouge foncé

    @SerialName("poison")
    POISON("poison", Color(50, 205, 50), Res.drawable.damage_poison),  // Vert lime

    @SerialName("psychic")
    PSYCHIC("psychic", Color(255, 20, 147), Res.drawable.damage_psychic),  // Rose vif

    @SerialName("radiant")
    RADIANT("radiant", Color(255, 255, 224), Res.drawable.damage_radiant),  // Jaune clair

    @SerialName("slashing")
    SLASHING("slashing", Color(178, 34, 34), Res.drawable.damage_slash),  // Rouge brique

    @SerialName("thunder")
    THUNDER("thunder", Color(30, 144, 255), Res.drawable.damage_thunder);  // Bleu dodger

    companion object {
        fun fromIndex(index: String) = entries.find { it.index == index }
            ?: throw IllegalArgumentException("Invalid damage type index: $index")
    }

}