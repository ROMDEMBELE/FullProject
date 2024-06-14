package domain

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class Monster(
    val index: String,
    val name: String,
    val isFavorite: Boolean = false,
    val challenge: Challenge,
    val size: CreatureSize? = null,
    val type: CreatureType? = null,
    val alignment: Alignment? = null,
    val armors: Map<String, Int> = emptyMap(),
    val hitPoints: Int? = null,
    val hitPointsRoll: String? = null,
    val movements: Map<Movement, String>? = null,
    val abilities: Map<Ability, Int> = emptyMap(),
    val savingThrows: List<String> = emptyList(),
    val skills: List<String> = emptyList(),
    val damageVulnerabilities: List<String> = emptyList(),
    val damageResistances: List<String> = emptyList(),
    val damageImmunities: List<String> = emptyList(),
    val conditionImmunities: List<String> = emptyList(),
    val senses: String? = null,
    val passivePerception: Int? = null,
    val languages: String? = null,
    val xp: Int? = null,
    val specialAbilities: List<SpecialAbility> = emptyList(),
    val actions: List<Action> = emptyList(),
    val image: String? = null,
    val legendaryActions: List<Action> = emptyList()
) {

    @Serializable
    enum class CreatureType(val color: Color) {
        @SerialName("aberration")
        ABERRATION(Color(0xFF6A0DAD)), // Purple

        @SerialName("beast")
        BEAST(Color(0xFF00FF00)), // Green

        @SerialName("celestial")
        CELESTIAL(Color(0xFFFFD700)), // Gold

        @SerialName("construct")
        CONSTRUCT(Color(0xFFA9A9A9)), // Dark Gray

        @SerialName("dragon")
        DRAGON(Color(0xFFFF4500)), // Orange Red

        @SerialName("elemental")
        ELEMENTAL(Color(0xFF1E90FF)), // Dodger Blue

        @SerialName("fey")
        FEY(Color(0xFFFF69B4)), // Hot Pink

        @SerialName("fiend")
        FIEND(Color(0xFF8B0000)), // Dark Red

        @SerialName("giant")
        GIANT(Color(0xFFFF6347)), // Tomato

        @SerialName("humanoid")
        HUMANOID(Color(0xFF4682B4)), // Steel Blue

        @SerialName("monstrosity")
        MONSTROSITY(Color(0xFF2E8B57)), // Sea Green

        @SerialName("ooze")
        OOZE(Color(0xFF00CED1)), // Dark Turquoise

        @SerialName("plant")
        PLANT(Color(0xFF228B22)), // Forest Green

        @SerialName("undead")
        UNDEAD(Color(0xFF4B0082)); // Indigo
    }

    @Serializable
    enum class Movement {
        @SerialName("hover")
        HOVER,

        @SerialName("climb")
        CLIMB,

        @SerialName("walk")
        WALK,

        @SerialName("fly")
        FLY,

        @SerialName("swim")
        SWIM
    }

    @Serializable
    enum class CreatureSize {
        @SerialName("Tiny")
        TINY,

        @SerialName("Small")
        SMALL,

        @SerialName("Medium")
        MEDIUM,

        @SerialName("Large")
        LARGE,

        @SerialName("Huge")
        HUGE,

        @SerialName("Gargantuan")
        GARGANTUAN;
    }

    @Serializable
    enum class Senses {
        @SerialName("darkvision")
        DARKVISION,

        @SerialName("blindsight")
        BLINDSIGHT,

        @SerialName("truesight")
        TRUESIGHT,

        @SerialName("passive_perception")
        PASSIVE_PERCEPTION
    }

    @Serializable
    enum class Alignment {
        @SerialName("any alignment")
        ANY_ALIGNMENT,

        @SerialName("unaligned")
        UNALIGNED,

        @SerialName("chaotic evil")
        CHAOTIC_EVIL,

        @SerialName("chaotic good")
        CHAOTIC_GOOD,

        @SerialName("chaotic neutral")
        CHAOTIC_NEUTRAL,

        @SerialName("lawful evil")
        LAWFUL_EVIL,

        @SerialName("lawful good")
        LAWFUL_GOOD,

        @SerialName("lawful neutral")
        LAWFUL_NEUTRAL,

        @SerialName("neutral")
        NEUTRAL,

        @SerialName("neutral evil")
        NEUTRAL_EVIL,

        @SerialName("neutral good")
        NEUTRAL_GOOD
    }

    data class SpecialAbility(
        val name: String,
        val desc: String
    )

    abstract class Action {
        abstract val name: String
        abstract val desc: String
    }

    data class MultiAttackAction(
        override val name: String,
        override val desc: String,
        val attacks: List<AttackAction>
    ) : Action()

    data class AttackAction(
        override val name: String,
        override val desc: String,
        val damage: List<Damage>,
        val bonus: Int,
    ) : Action()

    /**
     * Attack with range and Saving Throw of Dive
     */
    data class PowerAction(
        override val name: String,
        override val desc: String,
        val damage: List<Damage>,
        val recharge: String,
        val save: String,
    ) : Action()

    data class SimpleAction(
        override val name: String,
        override val desc: String,
    ) : Action()

    data class Damage(
        val type: String,
        val dice: String,
        val notes: String? = null,
    )
}

