package domain.model.monster

import androidx.compose.runtime.Composable
import domain.model.Ability
import domain.model.Level
import domain.model.SavingThrow
import domain.model.spell.Spell
import org.jetbrains.compose.resources.stringResource

open class SpecialAbility(
    override val name: String,
    override val desc: String,
) : MonsterPropertyDescription {

    data class SpellCasting(
        override val index: String,
        override val name: String,
        override val level: Level,
        override var isFavorite: Boolean = false,
        override val details: Details? = null,
        val notes: String? = null,
        val usage: String? = null,
    ) : Spell(
        index,
        name,
        level,
        isFavorite,
        details
    )

    open class SavingThrowAbility(
        override val name: String,
        override val desc: String,
        open val savingThrow: SavingThrow,
    ) : SpecialAbility(name, desc)

    open class InnateSpellCastingAbility(
        override val name: String,
        override val desc: String,
        override val savingThrow: SavingThrow,
        open val components: List<String>,
        val spellByUsage: Map<String, List<SpellCasting>> = emptyMap()
    ) : SavingThrowAbility(name, desc, savingThrow) {

        @Composable
        open fun buildDescription(name: String): String {
            return buildString {
                val s = stringResource(savingThrow.ability.stringRes)
                append("The $name spell casting ability $s")
                append(" (spell save DC ${savingThrow.value})")
            }
        }
    }

    data class SpellCastingAbility(
        override val name: String,
        override val desc: String,
        override val savingThrow: SavingThrow,
        override val components: List<String>,
        val spellByLevel: Map<Level, List<SpellCasting>> = emptyMap(),
        val level: Level,
        val ability: Ability,
        val modifier: Int,
        val dc: Int,
        val school: String,
        val slots: Map<Level, Int>,
    ) : InnateSpellCastingAbility(name, desc, savingThrow, components) {

        @Composable
        override fun buildDescription(name: String): String {
            return buildString {
                val s = stringResource(ability.stringRes)
                append("The $name is an ${level.level}th-level spell caster")
                append(" with $s spell casting ability")
                append(" (spell save DC ${dc}, +${modifier} to hit with spell attacks)")
            }
        }
    }
}