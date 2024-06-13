package ui.monster

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import domain.Ability
import domain.Ability.Companion.getModifier
import domain.Monster
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
import org.dembeyo.shared.resources.monster_senses
import org.dembeyo.shared.resources.monster_speed
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import ui.MediumBold
import ui.SmallBold
import ui.darkBlue
import ui.darkGray
import ui.darkPrimary
import ui.monsterPropertyText
import ui.monsterPropertyTitle
import ui.monsterSubTitle
import ui.monsterTitle
import ui.orange
import ui.primary
import ui.secondary

class MonsterDetailScreen(private val monster: Monster) : Screen {

    @Composable
    override fun Content() {
        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
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
                    append(monster.size?.description)
                    append(" ")
                    append(monster.type?.description)
                    append(", ")
                    append(monster.alignment?.description)
                }
                Text(
                    text = subtitle,
                    style = monsterSubTitle,
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                TaperedRule()

                PropertyLine(Res.string.monster_armor_class, monster.armorClass.toString())

                val life: String = buildString {
                    append(monster.hitPoints)
                    append(" (")
                    append(monster.hitPointsRoll)
                    append(")")
                }
                PropertyLine(Res.string.monster_hit_points, life)

                val speed = buildString {
                    append(monster.walkSpeed.toString())
                    append(" ft.")
                    if (monster.flySpeed != null)
                        append(" (fly " + monster.flySpeed.toString() + " ft.)")
                    if (monster.swimSpeed != null)
                        append(" (swim " + monster.swimSpeed.toString() + " ft.)")
                }
                PropertyLine(Res.string.monster_speed, speed)

                TaperedRule()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Ability.entries.forEach { ability ->
                        val value: Int = when (ability) {
                            Ability.CHA -> monster.charisma ?: 0
                            Ability.CON -> monster.constitution ?: 0
                            Ability.DEX -> monster.dexterity ?: 0
                            Ability.INT -> monster.intelligence ?: 0
                            Ability.STR -> monster.strength ?: 0
                            Ability.WIS -> monster.wisdom ?: 0
                        }
                        AbilityScope(ability.name, value, value.getModifier())
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
                    PropertyLine(Res.string.monster_senses, monster.senses.toString())

                    if (!monster.languages.isNullOrEmpty())
                        PropertyLine(Res.string.monster_languages, monster.languages.toString())

                    PropertyLine(
                        Res.string.monster_challenge_rating,
                        monster.challenge.rating.toString() + " (${monster.xp} XP)"
                    )
                }
                TaperedRule()

                monster.specialAbilities.forEach {
                    PropertyLine(it.name, it.desc)
                }

                TaperedRule()
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
    fun RowScope.AbilityScope(name: String, value: Int, bonus: Int) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 1.dp)
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(darkBlue)
        ) {
            Text(
                text = "$name $value",
                style = MediumBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(2.dp)
            )
            Text(
                text = "$bonus",
                modifier = Modifier.fillMaxWidth().background(darkGray).padding(2.dp),
                textAlign = TextAlign.Center,
                style = MediumBold
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
    fun PropertyLine(title: String, value: String) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 2.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(secondary),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
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
    fun PropertyLine(title: StringResource, value: String) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 2.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(secondary),
            horizontalArrangement = Arrangement.SpaceBetween
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
    fun ActionItem(action: Monster.Action) {
        val expended by remember { mutableStateOf(true) }
        val title = remember(action) {
            buildString {
                append(action.name)
                if (action is Monster.PowerAction) {
                    append(" - ${action.recharge}")
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

            AnimatedVisibility(expended) {
                Text(
                    text = action.desc.capitalize(Locale.current),
                    modifier = Modifier.padding(8.dp),
                    style = monsterPropertyText.copy(textAlign = TextAlign.Center)
                )
            }

            if (action is Monster.PowerAction) {
                val damageText = remember(action.damage) {
                    buildString {
                        action.damage.forEach { damage ->
                            if (!damage.notes.isNullOrEmpty()) append("${damage.notes} : ")
                            append("(${damage.dice} ${damage.type}) ")
                        }
                    }
                }
                Text(
                    text = "${action.save} of $damageText",
                    style = SmallBold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(primary)
                        .padding(4.dp)
                )
            }

            if (action is Monster.AttackAction) {
                val damageText = remember(action.damage) {
                    buildString {
                        action.damage.forEach { damage ->
                            if (!damage.notes.isNullOrEmpty()) append("${damage.notes} : ")
                            append("(${damage.dice} ${damage.type}) ")
                        }
                    }
                }
                Text(
                    text = "+${action.bonus} $damageText",
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
