package ui.monster

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import domain.Ability
import domain.Ability.Companion.getModifier
import domain.Monster
import ui.brown

class MonsterDetailScreen(private val monster: Monster) : Screen {

    @Composable
    override fun Content() {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFDF1DC), shape = RoundedCornerShape(4.dp)),
            color = MaterialTheme.colors.background
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Divider(color = Color(0xFFE69A28), thickness = 5.dp)

                Text(
                    text = monster.name,
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    color = brown,
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier.padding(top = 10.dp, bottom = 0.dp)
                )
                Text(
                    text = buildString {
                        append(monster.size!!.description)
                        append(" ")
                        append(monster.type!!.description)
                        append(", ")
                        append(monster.alignment!!.description)
                    },
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                TaperedRule()
                Column {
                    PropertyLine("Armor Class", monster.armorClass.toString())
                    PropertyLine(
                        "Hit Points",
                        "${monster.hitPoints.toString()} (${monster.hitPointsRoll.toString()})"
                    )
                    PropertyLine("Speed", monster.walkSpeed.toString())
                }
                TaperedRule()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Ability.entries.forEach { ability ->
                        val value = when (ability) {
                            Ability.CHA -> monster.charisma
                            Ability.CON -> monster.constitution
                            Ability.DEX -> monster.dexterity
                            Ability.INT -> monster.intelligence
                            Ability.STR -> monster.strength
                            Ability.WIS -> monster.wisdom
                        }
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = ability.name,
                                fontSize = 14.sp,
                                color = brown,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = value.toString() + "(${value!!.getModifier()})",
                                fontSize = 12.sp,
                                color = brown
                            )
                        }
                    }

                }

                TaperedRule()

                Column {
                    PropertyLine("Damage Immunities", monster.damageImmunities.joinToString())
                    PropertyLine("Condition Immunities", monster.conditionImmunities.joinToString())
                    PropertyLine("Senses", monster.senses.joinToString())
                    PropertyLine("Languages", monster.languages.toString())
                    PropertyLine("Challenge", monster.challenge.rating.toString() + " (${monster.xp} XP)")
                }
                TaperedRule()
                SpecialAbilities(monster.specialAbilities)
                TaperedRule()
                ActionsSection(monster.actions)
                Divider(color = Color(0xFFE69A28), thickness = 5.dp)
            }
        }
    }

    @Composable
    fun TaperedRule() {
        Divider(
            color = brown,
            thickness = 2.dp,
            modifier = Modifier.padding(vertical = 10.dp)
        )
    }

    @Composable
    fun PropertyLine(title: String, value: String) {
        Row(
            modifier = Modifier.padding(vertical = 4.dp).clip(RoundedCornerShape(20.dp))
                .background(color = Color.White)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                color = brown,
                fontSize = 13.5.sp,
                lineHeight = 16.sp,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = value,
                color = brown,
                fontSize = 13.5.sp,
                lineHeight = 16.sp
            )
        }
    }

    @Composable
    fun Abilities(abilities: List<Monster.SpecialAbility>) {
        Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
            Row {
                abilities.forEach { (name, description) ->
                    Column(
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .width(60.dp),
                        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color(0xFF7A200D),
                            modifier = Modifier.padding(bottom = 2.dp)
                        )
                        Text(
                            text = description,
                            color = Color(0xFF922610),
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun SpecialAbilities(specialAbilities: List<Monster.SpecialAbility>) {
        Column {
            specialAbilities.forEach { ability ->
                PropertyBlock(ability.name, ability.desc)
            }
        }
    }

    @Composable
    fun PropertyBlock(title: String, description: String) {
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            Text(
                text = title,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                fontSize = 13.5.sp,
                color = Color(0xFF7A200D),
                lineHeight = 16.sp
            )
            Text(
                text = description,
                fontSize = 13.5.sp,
                color = Color(0xFF922610),
                lineHeight = 16.sp
            )
        }
    }

    @Composable
    fun ActionsSection(actions: List<Monster.Action>) {
        Column {
            Text(
                text = "Actions",
                fontSize = 21.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.Serif,
                color = Color(0xFF7A200D),
                modifier = Modifier.padding(vertical = 20.dp)
            )
            actions.forEach { action ->
                PropertyBlock(action.name, action.desc)
            }
        }
    }

}
