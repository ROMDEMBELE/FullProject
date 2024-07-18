package ui.character

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.model.character.Character
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.castle_empty
import org.dembeyo.shared.resources.create_campaign_button
import org.dembeyo.shared.resources.create_character_button
import org.dembeyo.shared.resources.knight
import org.dembeyo.shared.resources.life_bar
import org.dembeyo.shared.resources.magic
import org.dembeyo.shared.resources.shield
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import ui.campaign.edit.EditCampaignScreen
import ui.character.edit.EditCharacterScreen
import ui.composable.BigBold
import ui.composable.CustomAnimatedPlaceHolder
import ui.composable.CustomButton
import ui.composable.MediumBold
import ui.composable.RoundIconText
import ui.composable.SmallBold
import ui.composable.TaperedRule
import ui.composable.darkPrimary
import ui.composable.roundCornerShape
import ui.composable.screenTitle
import ui.composable.secondary

class CharacterListScreen() : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val viewModel: CharacterViewModel = koinInject()
        val uiState: CharacterListUiState by viewModel.uiState.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.fetchCampaign()
        }

        AnimatedContent(uiState, transitionSpec = { fadeIn().togetherWith(fadeOut()) }) { state ->
            if (state.isReady.not()) {
                CustomAnimatedPlaceHolder(backgroundColor = darkPrimary, contentColor = secondary)
            } else if (state.isReady && state.campaign == null) {
                Column(
                    Modifier.fillMaxSize().background(secondary),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        "There is no campaign in progress, please create or select a new campaign",
                        style = BigBold,
                        modifier = Modifier.padding(16.dp)
                    )

                    TaperedRule(modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp))

                    CustomButton(onClick = { navigator.push(EditCampaignScreen()) }) {
                        Text(stringResource(Res.string.create_campaign_button))
                        Spacer(Modifier.width(8.dp))
                        Image(
                            painter = painterResource(Res.drawable.castle_empty),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(secondary),
                            modifier = Modifier.size(24.dp).aspectRatio(1f)
                        )
                    }
                }
            } else if (state.isReady && state.campaign != null) {
                Column(
                    Modifier.fillMaxSize().background(secondary),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        state.campaign.name,
                        style = screenTitle(),
                        modifier = Modifier.padding(8.dp).fillMaxWidth()
                    )

                    TaperedRule()

                    if (state.characters.isEmpty()) {
                        Spacer(Modifier.weight(1f))
                        Text("There is no character", style = BigBold)
                        Spacer(Modifier.weight(1f))
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            items(state.characters) { item ->
                                CharacterItem(
                                    character = item,
                                    onEdit = { navigator.push(EditCharacterScreen(item.id)) },
                                )
                            }
                        }
                    }
                    TaperedRule()
                    CustomButton(
                        modifier = Modifier.fillMaxWidth()
                            .padding(bottom = 8.dp, start = 8.dp, end = 8.dp),
                        onClick = {
                            navigator.push(EditCharacterScreen())
                        }
                    ) {
                        Text(stringResource(Res.string.create_character_button))
                    }
                }
            }
        }
    }


    @Composable
    fun CharacterItem(character: Character, onEdit: () -> Unit) {
        var moreExpanded by rememberSaveable { mutableStateOf(false) }
        Surface(
            shape = roundCornerShape,
            color = darkPrimary,
            modifier = Modifier.padding(bottom = 8.dp).clickable {
                moreExpanded = !moreExpanded
            }
        ) {
            Column(Modifier.padding(8.dp)) {
                ConstraintLayout(Modifier.fillMaxWidth().height(60.dp)) {
                    val (picture, characterName, playerName, editButton, moreButton) = createRefs()

                    RoundIconText(
                        drawable = Res.drawable.knight,
                        text = "Lv${character.level.level}",
                        modifier = Modifier
                            .constrainAs(picture) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                            },
                    )

                    Text(
                        text = character.fullName,
                        color = secondary,
                        style = MediumBold,
                        modifier = Modifier
                            .constrainAs(characterName) {
                                top.linkTo(picture.top)
                                start.linkTo(picture.end, margin = 6.dp)
                            }
                    )
                    Text(
                        text = "(${character.player})",
                        color = secondary,
                        style = SmallBold.copy(fontStyle = FontStyle.Italic),
                        modifier = Modifier
                            .alpha(0.5f)
                            .constrainAs(playerName) {
                                top.linkTo(characterName.bottom, margin = 2.dp)
                                start.linkTo(characterName.start)
                            }
                    )

                    IconButton(
                        modifier = Modifier.size(15.dp)
                            .constrainAs(editButton) {
                                top.linkTo(parent.top)
                                end.linkTo(parent.end)
                            }
                            .aspectRatio(1f),
                        onClick = onEdit)
                    {
                        Icon(Icons.Filled.Edit, null, tint = secondary)
                    }

                    IconButton(
                        modifier = Modifier.size(18.dp)
                            .constrainAs(moreButton) {
                                bottom.linkTo(parent.bottom)
                                end.linkTo(parent.end)
                                start.linkTo(parent.start)
                            }
                            .aspectRatio(1f),
                        onClick = { moreExpanded = !moreExpanded })
                    {
                        Crossfade(moreExpanded) { extended ->
                            if (extended) {
                                Icon(
                                    Icons.Filled.KeyboardArrowUp, null,
                                    tint = secondary,
                                )
                            } else {
                                Icon(
                                    Icons.Filled.KeyboardArrowDown, null,
                                    tint = secondary
                                )
                            }
                        }
                    }
                }

                AnimatedVisibility(moreExpanded) {
                    Row(
                        Modifier.fillMaxWidth().height(60.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        RoundIconText(
                            drawable = Res.drawable.shield,
                            text = "${character.armorClass}",
                            modifier = Modifier.padding(8.dp)
                        )

                        RoundIconText(
                            drawable = Res.drawable.life_bar,
                            text = "${character.hitPoint}",
                            modifier = Modifier.padding(8.dp)
                        )

                        RoundIconText(
                            drawable = Res.drawable.magic,
                            text = "${character.spellSavingThrow}",
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}