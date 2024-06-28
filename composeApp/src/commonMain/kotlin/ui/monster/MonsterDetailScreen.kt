package ui.monster

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.model.Ability
import domain.model.Ability.Companion.getAbilityBonus
import domain.model.Ability.Companion.getAbilityBonusColor
import domain.model.monster.Action
import domain.model.monster.Monster
import domain.model.monster.SpecialAbility
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.monster_actions
import org.dembeyo.shared.resources.monster_armor_class
import org.dembeyo.shared.resources.monster_challenge_rating
import org.dembeyo.shared.resources.monster_condition_immunities
import org.dembeyo.shared.resources.monster_damage_immunities
import org.dembeyo.shared.resources.monster_damage_resistances
import org.dembeyo.shared.resources.monster_damage_vulnerabilities
import org.dembeyo.shared.resources.monster_hit_points
import org.dembeyo.shared.resources.monster_languages
import org.dembeyo.shared.resources.monster_legendary_actions
import org.dembeyo.shared.resources.monster_proficiencies
import org.dembeyo.shared.resources.monster_saving_throws
import org.dembeyo.shared.resources.monster_senses
import org.dembeyo.shared.resources.monster_speed
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.composable.MediumBold
import ui.composable.SmallBold
import ui.composable.darkBlue
import ui.composable.darkGray
import ui.composable.darkPrimary
import ui.composable.lightGray
import ui.composable.monsterPropertyText
import ui.composable.monsterPropertyTitle
import ui.composable.monsterSubTitle
import ui.composable.monsterTitle
import ui.composable.orange
import ui.composable.primary
import ui.composable.secondary
import ui.spell.SpellDetailsScreen

class MonsterDetailScreen(private val monster: Monster) : Screen {

    private val details = monster.details ?: throw IllegalStateException("Monster Details is null")

    override val key: ScreenKey
        get() = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        var spellDialogDisplayed by rememberSaveable { mutableStateOf(false) }

        details.specialAbilities.find { it is SpecialAbility.SpellCastingAbility || it is SpecialAbility.InnateSpellCastingAbility }
            ?.let {
                AnimatedVisibility(spellDialogDisplayed) {
                    SpellDialog(it, navigator) { spellDialogDisplayed = false }
                }
            }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .background(
                    Brush.linearGradient(
                        listOf(
                            lightGray,
                            secondary,
                            monster.challenge.color
                        )
                    )
                )
                .padding(horizontal = 12.dp)
        ) {
            item {
                Divider(
                    color = orange,
                    thickness = 5.dp,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                // Monster X
                Text(text = monster.name, style = monsterTitle)
                // Size, Type of Creature, Alignment
                val subtitle = buildString {
                    append(details.size.fullName)
                    append(" ")
                    append(details.type.fullName)
                    append(", ")
                    append(details.alignment.fullName)
                }
                Text(
                    text = subtitle,
                    style = monsterSubTitle,
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                TaperedRule()

                val armorClass =
                    details.armorsClass.entries.joinToString { "${it.value} (${it.key})" }
                PropertyLine(Res.string.monster_armor_class, armorClass)

                val life: String = buildString {
                    append(details.hitPoints)
                    append(" ( ")
                    append(details.hitPointsRoll)
                    append(" )")
                }
                PropertyLine(Res.string.monster_hit_points, life)

                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(vertical = 2.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(secondary),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = stringResource(Res.string.monster_speed),
                        style = monsterPropertyTitle,
                        modifier = Modifier.padding(4.dp).weight(1f)
                    )
                    details.speedByMovements.entries.forEach { (movement, value) ->
                        Icon(
                            modifier = Modifier.size(20.dp).aspectRatio(1f).padding(2.dp),
                            painter = painterResource(movement.icon),
                            contentDescription = null,
                            tint = darkPrimary
                        )
                        Text(
                            text = "${movement.fullName} $value",
                            modifier = Modifier.padding(4.dp),
                            textAlign = TextAlign.End,
                            style = monsterPropertyText,
                        )
                    }
                }

                TaperedRule()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Ability.entries.forEach { ability ->
                        when (ability) {
                            Ability.STR -> AbilityBonusItem(ability.fullName, details.strength)
                            Ability.DEX -> AbilityBonusItem(ability.fullName, details.dexterity)
                            Ability.CON -> AbilityBonusItem(ability.fullName, details.constitution)
                            Ability.INT -> AbilityBonusItem(ability.fullName, details.intelligence)
                            Ability.WIS -> AbilityBonusItem(ability.fullName, details.wisdom)
                            Ability.CHA -> AbilityBonusItem(ability.fullName, details.charisma)
                        }
                    }
                }

                TaperedRule()

                Column {
                    if (details.damageVulnerabilities.isNotEmpty()) {
                        PropertyLine(
                            Res.string.monster_damage_vulnerabilities,
                            details.damageVulnerabilities.joinToString()
                        )
                    }
                    if (details.damageImmunities.isNotEmpty()) {
                        PropertyLine(
                            Res.string.monster_damage_immunities,
                            details.damageImmunities.joinToString()
                        )
                    }
                    if (details.damageResistances.isNotEmpty()) {
                        PropertyLine(
                            Res.string.monster_damage_resistances,
                            details.damageResistances.joinToString()
                        )
                    }
                    if (details.conditionImmunities.isNotEmpty()) {
                        PropertyLine(
                            Res.string.monster_condition_immunities,
                            details.conditionImmunities.joinToString()
                        )
                    }

                    val sensesText =
                        details.senses.entries.map { (key, value) -> "${stringResource(key.fullName)} $value" }
                    PropertyLine(Res.string.monster_senses, sensesText.joinToString())

                    if (details.languages.isNotEmpty())
                        PropertyLine(Res.string.monster_languages, details.languages)

                    PropertyLine(
                        Res.string.monster_challenge_rating,
                        monster.challenge.rating.toString() + " (${details.xp} XP)"
                    )
                    if (details.skills.isNotEmpty()) {
                        PropertyLine(
                            Res.string.monster_proficiencies,
                            details.skills.joinToString()
                        )
                    }
                    if (details.savingThrows.isNotEmpty()) {
                        PropertyLine(
                            Res.string.monster_saving_throws,
                            details.savingThrows.joinToString()
                        )
                    }
                }
                TaperedRule()

                if (details.specialAbilities.isNotEmpty()) {
                    details.specialAbilities.forEach {
                        when (it) {
                            is SpecialAbility.InnateSpellCastingAbility, is SpecialAbility.SpellCastingAbility -> {
                                SpellCastingAbilityItem(monster, it) { spellDialogDisplayed = true }
                            }

                            else -> SpecialAbilityItem(it)
                        }
                    }
                    TaperedRule()
                }

                Text(
                    text = stringResource(Res.string.monster_actions),
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily.Serif,
                    color = darkPrimary,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
                details.actions.forEach { action ->
                    ActionItem(action)
                }

                if (details.legendaryActions.isNotEmpty()) {
                    TaperedRule()
                    Text(
                        text = stringResource(Res.string.monster_legendary_actions),
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily.Serif,
                        color = darkPrimary,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )

                    details.legendaryActions.forEach { action ->
                        ActionItem(action)
                    }
                }

                Divider(
                    color = orange,
                    thickness = 5.dp,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }
        }
    }

    @Composable
    fun RowScope.AbilityBonusItem(abilityName: String, abilityValue: Int) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 1.dp)
                .weight(1f)
                .clip(RoundedCornerShape(4.dp))
                .background(darkPrimary)
        ) {
            Text(
                text = "$abilityName $abilityValue",
                style = SmallBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(2.dp)
            )
            val signedBonus =
                if (abilityValue.getAbilityBonus() > 0) "+${abilityValue.getAbilityBonus()}" else "${abilityValue.getAbilityBonus()}"
            Text(
                text = signedBonus,
                modifier = Modifier.fillMaxWidth().background(abilityValue.getAbilityBonusColor())
                    .padding(2.dp),
                textAlign = TextAlign.Center,
                style = MediumBold.copy(color = darkPrimary)
            )
        }
    }

    @Composable
    fun TaperedRule() {
        Divider(
            color = darkPrimary,
            thickness = 2.dp,
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }

    @Composable
    fun PropertyLine(title: StringResource, value: String) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 2.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(secondary),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(title),
                style = monsterPropertyTitle,
                modifier = Modifier.padding(4.dp)
            )
            Text(
                text = value.capitalize(Locale.current),
                modifier = Modifier.padding(4.dp),
                textAlign = TextAlign.End,
                style = monsterPropertyText,
            )
        }
    }

    @Composable
    fun SpecialAbilityItem(ability: SpecialAbility) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 2.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(secondary),
        ) {
            Text(
                text = ability.name,
                style = SmallBold.copy(color = secondary),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(darkPrimary)
                    .padding(4.dp)
            )
            Text(
                text = ability.desc.capitalize(Locale.current),
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                style = monsterPropertyText.copy(textAlign = TextAlign.Center)
            )
        }
    }

    @Composable
    fun <T> SpellCastingAbilityItem(
        monster: Monster,
        ability: T,
        onClick: () -> Unit,
    ) where T : SpecialAbility {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 2.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(secondary),
        ) {
            Text(
                text = ability.name,
                style = SmallBold.copy(color = secondary),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(darkPrimary)
                    .padding(4.dp)
            )
            val description: String = when (ability) {
                is SpecialAbility.InnateSpellCastingAbility -> ability.buildDescription(monster.name)
                is SpecialAbility.SpellCastingAbility -> ability.buildDescription(monster.name)
                else -> throw IllegalArgumentException("Unknown ability type")
            }
            Text(
                text = description.capitalize(Locale.current),
                modifier = Modifier.padding(8.dp),
                style = monsterPropertyText.copy(textAlign = TextAlign.Center)
            )
            TextButton(
                onClick = onClick,
                colors = ButtonDefaults.textButtonColors(contentColor = darkPrimary),
                modifier = Modifier.fillMaxWidth().height(30.dp)
            ) {
                Text("See More")
            }
        }
    }

    @Composable
    fun ActionItem(action: Action) {
        val title: String = remember(action) {
            when (action) {
                is Action.MultiAttackAction -> {
                    buildString {
                        append(action.name)
                        append(" ")
                        append("(${action.attacks.distinct().joinToString()})")
                    }
                }

                else -> {
                    buildString {
                        append(action.name)
                        if (!action.usage.isNullOrEmpty()) append("(${action.usage})")
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(secondary),
        ) {
            Text(
                text = title,
                style = SmallBold,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(darkBlue)
                    .padding(4.dp)
            )

            Text(
                text = action.desc.capitalize(Locale.current),
                modifier = Modifier.padding(8.dp),
                style = monsterPropertyText.copy(textAlign = TextAlign.Center)
            )

            when (action) {
                is Action.SavingThrowAction -> {
                    val damageText = remember(action.damage) {
                        action.damage.joinToString { damage ->
                            when {
                                !damage.notes.isNullOrEmpty() -> "${damage.notes} : (${damage.dice} ${damage.type})"
                                else -> "${damage.dice} ${damage.type}"
                            }
                        }
                    }
                    Text(
                        text = "${action.savingThrow} of $damageText",
                        style = SmallBold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(primary)
                            .padding(4.dp)
                    )
                }

                is Action.AttackAction -> {
                    val damageText = remember(action.damage) {
                        action.damage.joinToString { damage ->
                            when {
                                !damage.notes.isNullOrEmpty() -> "${damage.notes} : (${damage.dice} ${damage.type})"
                                else -> "${damage.dice} ${damage.type}"
                            }
                        }
                    }
                    Text(
                        text = "+${action.attackBonus} $damageText",
                        style = SmallBold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(darkGray)
                            .padding(4.dp)
                    )
                }
            }
        }
    }

    @Composable
    fun <T> SpellDialog(
        ability: T,
        navigator: Navigator,
        onDismissRequest: () -> Unit
    ) where T : SpecialAbility {
        Dialog(
            onDismissRequest = onDismissRequest,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(secondary),
            ) {
                Text(
                    text = ability.name,
                    style = SmallBold.copy(color = secondary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(darkPrimary)
                        .padding(4.dp)
                )
                LazyColumn(
                    Modifier.height(400.dp).fillMaxWidth(),
                    contentPadding = PaddingValues(0.dp),
                    verticalArrangement = Arrangement.Center,
                ) {
                    when (ability) {
                        is SpecialAbility.SpellCastingAbility -> {
                            ability.slots.forEach { (level, slot) ->
                                item(level) {
                                    Text(
                                        text = "Lv${level.level} ($slot slots)",
                                        modifier = Modifier.background(darkGray).fillMaxWidth()
                                            .padding(4.dp),
                                        color = level.color,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center,
                                    )
                                }
                                items(items = ability.spellByLevel[level].orEmpty()) { spell ->
                                    TextButton(
                                        modifier = Modifier.background(level.color).fillMaxWidth(),
                                        colors = ButtonDefaults.textButtonColors(
                                            contentColor = darkPrimary,
                                        ),
                                        onClick = {

                                        }) {
                                        Text(text = spell.name.capitalize(Locale.current))
                                    }
                                }
                            }
                        }

                        is SpecialAbility.InnateSpellCastingAbility -> {
                            ability.spellByUsage.forEach { (usage, spells) ->
                                item(usage) {
                                    Text(
                                        text = usage.capitalize(Locale.current),
                                        modifier = Modifier.background(darkGray)
                                            .fillMaxWidth()
                                            .padding(4.dp),
                                        color = secondary,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center,
                                    )
                                }
                                items(items = spells) { spell ->
                                    TextButton(
                                        modifier = Modifier.background(spell.level.color)
                                            .fillMaxWidth(),
                                        colors = ButtonDefaults.textButtonColors(darkPrimary),
                                        onClick = {
                                            navigator.push(SpellDetailsScreen(spell))
                                        })
                                    {
                                        Text(text = spell.name.capitalize(Locale.current))
                                    }
                                }
                            }

                        }


                    }

                }
            }

        }
    }
}
