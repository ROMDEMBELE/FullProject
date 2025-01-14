package ui.monster.details

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import domain.model.Ability
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.burrow
import org.dembeyo.shared.resources.button_add_to_favorite
import org.dembeyo.shared.resources.button_remove_from_favorites
import org.dembeyo.shared.resources.climb
import org.dembeyo.shared.resources.fly
import org.dembeyo.shared.resources.ghost
import org.dembeyo.shared.resources.hover
import org.dembeyo.shared.resources.monster_actions
import org.dembeyo.shared.resources.monster_armor_class
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
import org.dembeyo.shared.resources.monster_senses_blind_sight
import org.dembeyo.shared.resources.monster_senses_dark_vision
import org.dembeyo.shared.resources.monster_senses_passive_perception
import org.dembeyo.shared.resources.monster_senses_tremor_sense
import org.dembeyo.shared.resources.monster_senses_true_sight
import org.dembeyo.shared.resources.monster_special_abilities
import org.dembeyo.shared.resources.monster_speed
import org.dembeyo.shared.resources.non_magical_attack_immunity
import org.dembeyo.shared.resources.non_magical_attack_resistance
import org.dembeyo.shared.resources.swim
import org.dembeyo.shared.resources.walk
import org.dembeyo.shared.resources.wing
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.color
import ui.composable.CustomAnimatedPlaceHolder
import ui.composable.CustomButton
import ui.composable.TaperedRule
import ui.composable.darkGray
import ui.composable.primaryDark
import ui.composable.lightGray
import ui.composable.monsterSubTitle
import ui.composable.monsterTitle
import ui.composable.propertyText
import ui.composable.propertyTitle
import ui.composable.roundCornerShape
import ui.composable.secondary
import ui.joinToString
import ui.monster.details.composable.ActionItem
import ui.stringRes

@Composable
fun MonsterDetailScreen(
    navController: NavHostController,
    viewModel: MonsterDetailsViewModel
) {

    val uiState by viewModel.state.collectAsState()

    AnimatedContent(uiState, transitionSpec = { fadeIn().togetherWith(fadeOut()) }) { state ->
        if (!state.isReady) {
            CustomAnimatedPlaceHolder()
        } else {
            val brush = Brush.linearGradient(listOf(lightGray, secondary, state.challenge.color()))
            LazyColumn(
                state = rememberLazyListState(),
                modifier = Modifier.fillMaxSize()
                    .background(brush)
                    .padding(horizontal = 8.dp)
            ) {
                item {
                    CustomDivider()
                    // Monster X
                    Text(text = state.name.toString(), style = monsterTitle)
                    Spacer(Modifier.height(4.dp))
                    // Size, Type of Creature, Alignment
                    val alignmentText = stringResource(state.alignment.stringRes())
                    val sizeText = stringResource(state.size.stringRes())
                    val typeText = stringResource(state.type.stringRes())
                    Text(text = "$sizeText $typeText, $alignmentText", style = monsterSubTitle)
                    TaperedRule()

                    // Armor Class 16 ( Plate Armor )
                    PropertyLine(Res.string.monster_armor_class, state.armorsClass.toString())

                    // Hit Points 10 3d6 + 12
                    PropertyLine(Res.string.monster_hit_points, state.hitPoints.toString())

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
                            style = propertyTitle,
                            modifier = Modifier.padding(4.dp).weight(1f)
                        )
                        speed(Res.string.walk, state.walkSpeed, Res.drawable.walk)
                        if (state.swimSpeed > 10) {
                            speed(
                                Res.string.swim,
                                state.swimSpeed,
                                Res.drawable.swim
                            )
                        }
                        if (state.flySpeed > 10) {
                            speed(
                                Res.string.fly,
                                state.flySpeed,
                                Res.drawable.wing
                            )
                        }
                        if (state.burrowSpeed > 10) {
                            speed(
                                Res.string.burrow,
                                state.burrowSpeed,
                                Res.drawable.ghost
                            )
                        }

                        if (state.climbSpeed > 10) {
                            speed(
                                Res.string.climb,
                                state.climbSpeed,
                                Res.drawable.climb
                            )
                        }
                        if (state.hover) {
                            speed(Res.string.hover, 0.0, Res.drawable.ghost)
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
                                Ability.STR -> AbilityChip(ability.name, state.strength)
                                Ability.DEX -> AbilityChip(ability.name, state.dexterity)
                                Ability.CON -> AbilityChip(ability.name, state.constitution)
                                Ability.INT -> AbilityChip(ability.name, state.intelligence)
                                Ability.WIS -> AbilityChip(ability.name, state.wisdom)
                                Ability.CHA -> AbilityChip(ability.name, state.charisma)
                            }
                        }
                    }

                    TaperedRule()

                    if (state.nonMagicalAttackResistance) {
                        PropertyLine(Res.string.non_magical_attack_resistance, "")
                    }

                    if (state.nonMagicalAttackImmunity) {
                        PropertyLine(Res.string.non_magical_attack_immunity, "")
                    }

                    if (state.hasVulnerabilities) {
                        val vulnerabilities =
                            state.damageVulnerabilities.joinToString(",") { stringResource(it.stringRes()) }
                        PropertyLine(Res.string.monster_damage_vulnerabilities, vulnerabilities)
                    }
                    if (state.hasImmunities) {
                        val immunities =
                            state.damageImmunities.joinToString(",") { stringResource(it.stringRes()) }
                        PropertyLine(Res.string.monster_damage_immunities, immunities)
                    }
                    if (state.hasResistances) {
                        val resistances =
                            state.damageResistances.joinToString(",") { stringResource(it.stringRes()) }
                        PropertyLine(Res.string.monster_damage_resistances, resistances)
                    }
                    if (state.hasConditionImmunities) {
                        val conditionImmunities =
                            state.conditionImmunities.joinToString(",") { stringResource(it.stringRes()) }
                        PropertyLine(
                            Res.string.monster_condition_immunities,
                            conditionImmunities
                        )
                    }

                    PropertyLine(
                        Res.string.monster_senses_passive_perception,
                        state.passivePerception.toString()
                    )

                    val senses = buildString {
                        if (state.darkVision != null) {
                            append("${stringResource(Res.string.monster_senses_dark_vision)} ${state.darkVision}")
                        }
                        if (state.trueSight != null) {
                            append("${stringResource(Res.string.monster_senses_true_sight)} ${state.trueSight} ")
                        }
                        if (state.tremorSense != null) {
                            append("${stringResource(Res.string.monster_senses_tremor_sense)} ${state.tremorSense} ")
                        }
                        if (state.blindSight != null) {
                            append("${stringResource(Res.string.monster_senses_blind_sight)} ${state.blindSight} ")
                        }
                    }

                    PropertyLine(Res.string.monster_senses, senses)

                    if (state.languages.isNotEmpty()) {
                        PropertyLine(
                            Res.string.monster_languages,
                            state.languages.joinToString()
                        )
                    }

                    if (state.skills.isNotEmpty()) {
                        PropertyLine(Res.string.monster_proficiencies,
                            state.skills.entries.joinToString { "${it.key} ${it.value}" })
                    }

                    if (state.hasSavingThrows) {
                        val savingThrows = buildString {
                            if (state.strengthSave != null) append("STR ${state.strengthSave} ")
                            if (state.dexteritySave != null) append("DEX ${state.dexteritySave} ")
                            if (state.constitutionSave != null) append("CON ${state.constitutionSave} ")
                            if (state.intelligenceSave != null) append("INT ${state.intelligenceSave} ")
                            if (state.wisdomSave != null) append("WIS ${state.wisdomSave} ")
                            if (state.charismaSave != null) append("CHA ${state.charismaSave} ")
                        }
                        PropertyLine(Res.string.monster_saving_throws, savingThrows)
                    }

                    TaperedRule()

                    Text(
                        text = stringResource(Res.string.monster_special_abilities),
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily.Serif,
                        color = primaryDark,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )

                    if (state.trait.isNotEmpty()) {
                        state.trait.forEach { ability ->
                            TraitItem(ability)
                        }
                        TaperedRule()
                    }

                    Text(
                        text = stringResource(Res.string.monster_actions),
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily.Serif,
                        color = primaryDark,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )

                    state.actions.forEach { action ->
                        ActionItem(action)
                    }

                    state.bonusActions.forEach { action ->
                        ActionItem(action)
                    }

                    state.reactions.forEach { action ->
                        ActionItem(action)
                    }

                    if (state.legendaryActions.isNotEmpty()) {
                        TaperedRule()
                        Text(
                            text = stringResource(Res.string.monster_legendary_actions),
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = FontFamily.Serif,
                            color = primaryDark,
                            modifier = Modifier.padding(vertical = 12.dp)
                        )

                        state.legendaryActions.forEach { action ->
                            ActionItem(action)
                        }
                    }
                    CustomDivider()

                    if (state.isFavorite) {
                        CustomButton(
                            modifier = Modifier.fillMaxWidth(),
                            content = { Text(stringResource(Res.string.button_remove_from_favorites)) },
                            onClick = viewModel::removeFromFavorites
                        )
                    } else {
                        CustomButton(
                            modifier = Modifier.fillMaxWidth(),
                            content = { Text(stringResource(Res.string.button_add_to_favorite)) },
                            onClick = viewModel::addToFavorites
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun header(text: String) {
    Text(
        text = text.capitalize(Locale.current),
        modifier = Modifier.height(30.dp).background(darkGray).fillMaxWidth().padding(6.dp),
        color = secondary,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
    )
}

@Composable
fun speed(movement: StringResource, value: Double, icon: DrawableResource) {
    Icon(
        modifier = Modifier.size(20.dp).aspectRatio(1f).padding(2.dp),
        painter = painterResource(icon),
        contentDescription = null,
        tint = primaryDark
    )
    val movementText = stringResource(movement)
    Text(
        text = "$movementText $value",
        modifier = Modifier.padding(4.dp),
        textAlign = TextAlign.End,
        style = propertyText,
    )
}

@Composable
fun spell(text: String, color: Color, onClick: () -> Unit) {
    TextButton(
        modifier = Modifier.padding(8.dp).height(40.dp).fillMaxWidth(),
        shape = roundCornerShape,
        colors = ButtonDefaults.textButtonColors(
            containerColor = color,
            contentColor = primaryDark
        ),
        onClick = onClick
    ) {
        Text(text = text.capitalize(Locale.current))
    }
}
