package ui.character

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import ui.campaign.edit.EditCampaignScreen
import ui.character.edit.EditCharacterScreen
import ui.composable.BigBold
import ui.composable.CustomButton
import ui.composable.MediumBold
import ui.composable.SmallBold
import ui.composable.TaperedRule
import ui.composable.darkBlue
import ui.composable.fadingEdge
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
            if (state.campaign == null) {
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
            } else {
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
                                .fadingEdge()
                        ) {
                            items(state.characters) { item ->
                                CharacterItem(
                                    character = item,
                                    onClick = { navigator.push(EditCharacterScreen(item.id)) },
                                    onLongClick = {}
                                )
                            }
                        }
                    }
                    TaperedRule()
                    CustomButton(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp, start = 8.dp, end = 8.dp),
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
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CharacterItem(character: Character, onClick: () -> Unit, onLongClick: () -> Unit) {
    Surface(
        shape = roundCornerShape,
        color = darkBlue,
        modifier = Modifier
            .padding(vertical = 4.dp)
            .height(60.dp)
            .fillMaxWidth()
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
    ) {
        ConstraintLayout(Modifier.fillMaxSize().padding(10.dp)) {
            val (profilePicture, characterName, level, playerName) = createRefs()

            Image(
                painter = painterResource(Res.drawable.knight),
                null,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(secondary)
                    .size(30.dp)
                    .aspectRatio(1f)
                    .constrainAs(profilePicture) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    },
                colorFilter = ColorFilter.tint(darkBlue)
            )

            Text(
                text = character.fullName,
                color = secondary,
                style = MediumBold,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(characterName) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
            Text(
                text = character.player,
                color = secondary,
                style = SmallBold,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(playerName) {
                        top.linkTo(characterName.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
            Text(
                text = "Lv${character.level.level}",
                textAlign = TextAlign.Center,
                style = SmallBold,
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(end = 4.dp)
                    .drawBehind {
                        drawCircle(
                            color = character.level.color,
                            radius = 40f
                        )
                    }
                    .constrainAs(level) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    },
                color = darkBlue
            )
        }

    }
}