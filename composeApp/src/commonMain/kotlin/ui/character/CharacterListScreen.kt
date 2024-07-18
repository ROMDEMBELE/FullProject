package ui.character

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.model.Campaign
import domain.model.character.Character
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.castle_empty
import org.dembeyo.shared.resources.create_character_button
import org.dembeyo.shared.resources.knight
import org.dembeyo.shared.resources.menu_campaign
import org.dembeyo.shared.resources.no_campaign
import org.dembeyo.shared.resources.no_character
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import ui.campaign.main.CampaignScreen
import ui.character.edit.EditCharacterScreen
import ui.composable.CustomAnimatedPlaceHolder
import ui.composable.CustomButton
import ui.composable.MediumBoldSecondary
import ui.composable.RoundIconText
import ui.composable.SmallBoldSecondary
import ui.composable.TaperedRule
import ui.composable.darkPrimary
import ui.composable.roundCornerShape
import ui.composable.screenTitle
import ui.composable.secondary

class CharacterListScreen() : Screen {

    @Composable
    override fun Content() {
        val viewModel: CharacterViewModel = koinInject()
        val uiState: CharacterListUiState by viewModel.uiState.collectAsState()

        Box(Modifier.fillMaxSize().background(darkPrimary)) {
            AnimatedContent(
                targetState = uiState,
                modifier = Modifier.align(Alignment.Center),
                transitionSpec = { fadeIn().togetherWith(fadeOut()) }) { state ->
                when {
                    state.isReady.not() -> {
                        CustomAnimatedPlaceHolder(
                            backgroundColor = darkPrimary,
                            contentColor = secondary
                        )
                    }

                    state.isReady && state.campaign == null -> {
                        NoCampaignScreen()
                    }

                    state.isReady && state.campaign != null -> {
                        CampaignCharacterScreen(state.campaign, state.characters)
                    }
                }
            }
        }
    }

    @Composable
    fun CampaignCharacterScreen(campaign: Campaign, characters: List<Character>) {
        val navigator = LocalNavigator.currentOrThrow
        Column(
            Modifier.fillMaxSize().background(secondary),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                campaign.name,
                style = screenTitle(),
                modifier = Modifier.fillMaxWidth()
            )
            TaperedRule()

            if (characters.isEmpty()) {
                Spacer(Modifier.weight(1f))
                Text(
                    stringResource(Res.string.no_character),
                    style = MediumBoldSecondary.copy(darkPrimary)
                )
                Spacer(Modifier.weight(1f))
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    items(characters) { item ->
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

    @Composable
    fun NoCampaignScreen() {
        val navigator = LocalNavigator.currentOrThrow
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.knight),
                contentDescription = null,
                colorFilter = ColorFilter.tint(secondary),
                modifier = Modifier.size(100.dp).aspectRatio(1f)
            )

            Spacer(Modifier.height(16.dp))

            Text(
                stringResource(Res.string.no_campaign),
                color = secondary,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )

            Spacer(Modifier.height(16.dp))

            CustomButton(
                onClick = { navigator.push(CampaignScreen()) },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = secondary,
                    contentColor = darkPrimary
                )
            ) {
                Image(
                    painter = painterResource(Res.drawable.castle_empty),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(darkPrimary),
                    modifier = Modifier.size(24.dp).aspectRatio(1f)
                )

                Spacer(Modifier.width(8.dp))

                Text(stringResource(Res.string.menu_campaign))
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
                ConstraintLayout(Modifier.fillMaxWidth().height(50.dp)) {
                    val (picture, characterName, playerName, editButton) = createRefs()

                    RoundIconText(
                        text = "Lv${character.level.level}",
                        backgroundColor = secondary,
                        iconColor = darkPrimary,
                        modifier = Modifier
                            .constrainAs(picture) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                bottom.linkTo(parent.bottom)
                            },
                    )

                    Text(
                        text = character.fullName,
                        color = secondary,
                        style = MediumBoldSecondary,
                        modifier = Modifier
                            .constrainAs(characterName) {
                                top.linkTo(picture.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(playerName.top)
                            }
                    )
                    Text(
                        text = "(${character.player})",
                        color = secondary,
                        style = SmallBoldSecondary.copy(fontStyle = FontStyle.Italic),
                        modifier = Modifier
                            .alpha(0.5f)
                            .constrainAs(playerName) {
                                top.linkTo(characterName.bottom)
                                start.linkTo(characterName.start)
                                end.linkTo(characterName.end)
                                bottom.linkTo(parent.bottom)
                            }
                    )

                    IconButton(
                        modifier = Modifier.size(40.dp).clip(CircleShape)
                            .background(secondary)
                            .padding(4.dp)
                            .constrainAs(editButton) {
                                top.linkTo(parent.top)
                                end.linkTo(parent.end)
                                bottom.linkTo(parent.bottom)
                            }
                            .aspectRatio(1f),
                        onClick = onEdit)
                    {
                        Icon(Icons.Filled.Edit, null, tint = darkPrimary)
                    }
                    /*
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

                     */
                }


                /*
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

                 */
            }
        }
    }
}