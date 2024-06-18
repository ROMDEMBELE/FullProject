package ui.monster

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
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
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.Ability.Companion.getAbilityBonus
import domain.Ability.Companion.getAbilityBonusColor
import domain.monster.Monster
import domain.monster.Monster.InnateSpellCastingAbility
import domain.monster.Monster.SpecialAbility
import domain.monster.Monster.SpellCastingAbility
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
import org.dembeyo.shared.resources.monster_proficiencies
import org.dembeyo.shared.resources.monster_saving_throws
import org.dembeyo.shared.resources.monster_senses
import org.dembeyo.shared.resources.monster_speed
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.MediumBold
import ui.SmallBold
import ui.darkBlue
import ui.darkGray
import ui.darkPrimary
import ui.lightGray
import ui.monsterPropertyText
import ui.monsterPropertyTitle
import ui.monsterSubTitle
import ui.monsterTitle
import ui.orange
import ui.primary
import ui.secondary

class MonsterDetailScreen(private val monster: Monster.MonsterDetails) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        var spellDialogDisplayed by rememberSaveable { mutableStateOf(false) }

        monster.specialAbilities.find { it is SpellCastingAbility || it is InnateSpellCastingAbility }
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
                    append(monster.size.fullName)
                    append(" ")
                    append(monster.type.fullName)
                    append(", ")
                    append(monster.alignment.fullName)
                }
                Text(
                    text = subtitle,
                    style = monsterSubTitle,
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                TaperedRule()

                val armorClass =
                    monster.armorsClass.entries.joinToString { "${it.value} (${it.key})" }
                PropertyLine(Res.string.monster_armor_class, armorClass)

                val life: String = buildString {
                    append(monster.hitPoints)
                    append(" ( ")
                    append(monster.hitPointsRoll)
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
                    monster.speedByMovements.entries.forEach { (movement, value) ->
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
                    monster.scoreByAbilities.entries.forEach { (ability, value) ->
                        AbilityBonusItem(ability.name, value)
                    }
                }

                TaperedRule()

                Column {
                    if (monster.damageVulnerabilities.isNotEmpty()) {
                        PropertyLine(
                            Res.string.monster_damage_vulnerabilities,
                            monster.damageVulnerabilities.joinToString()
                        )
                    }
                    if (monster.damageImmunities.isNotEmpty()) {
                        PropertyLine(
                            Res.string.monster_damage_immunities,
                            monster.damageImmunities.joinToString()
                        )
                    }
                    if (monster.damageResistances.isNotEmpty()) {
                        PropertyLine(
                            Res.string.monster_damage_resistances,
                            monster.damageResistances.joinToString()
                        )
                    }
                    if (monster.conditionImmunities.isNotEmpty()) {
                        PropertyLine(
                            Res.string.monster_condition_immunities,
                            monster.conditionImmunities.joinToString()
                        )
                    }

                    val sensesText =
                        monster.senses.entries.map { (key, value) -> "${stringResource(key.fullName)} $value" }
                    PropertyLine(Res.string.monster_senses, sensesText.joinToString())

                    if (monster.languages.isNotEmpty())
                        PropertyLine(Res.string.monster_languages, monster.languages)

                    PropertyLine(
                        Res.string.monster_challenge_rating,
                        monster.challenge.rating.toString() + " (${monster.xp} XP)"
                    )
                    if (monster.skills.isNotEmpty()) {
                        PropertyLine(
                            Res.string.monster_proficiencies,
                            monster.skills.joinToString()
                        )
                    }
                    if (monster.savingThrows.isNotEmpty()) {
                        PropertyLine(
                            Res.string.monster_saving_throws,
                            monster.savingThrows.joinToString()
                        )
                    }
                }
                TaperedRule()

                if (monster.specialAbilities.isNotEmpty()) {
                    monster.specialAbilities.forEach {
                        when (it) {
                            is InnateSpellCastingAbility, is SpellCastingAbility -> {
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
                monster.actions.forEach { action ->
                    ActionItem(action)
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
            TextButton(
                onClick = onClick,
                colors = ButtonDefaults.textButtonColors(contentColor = darkPrimary),
                modifier = Modifier.fillMaxWidth().height(30.dp)
            ) {
                Text("See More")
            }
            val description: String = when (ability) {
                is InnateSpellCastingAbility -> ability.buildDescription(monster.name)
                is SpellCastingAbility -> ability.buildDescription(monster.name)
                else -> throw IllegalArgumentException("Unknown ability type")
            }
            Text(
                text = description.capitalize(Locale.current),
                modifier = Modifier.padding(8.dp),
                style = monsterPropertyText.copy(textAlign = TextAlign.Center)
            )
        }
    }

    @Composable
    fun ActionItem(action: Monster.Action) {
        val title: String = remember(action) {
            when (action) {
                is Monster.MultiAttackAction -> {
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
                is Monster.SavingThrowAction -> {
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

                is Monster.AttackAction -> {
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

    @OptIn(ExperimentalFoundationApi::class)
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
                        is SpellCastingAbility -> {
                            ability.slots.forEach { (level, slot) ->
                                stickyHeader(level) {
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

                        is InnateSpellCastingAbility -> {
                            ability.spellByUsage.forEach { (usage, spells) ->
                                stickyHeader(usage) {
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


                    }

                }
            }

        }
    }
}
