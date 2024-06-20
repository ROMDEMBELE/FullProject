package ui.player

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.model.character.Character
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.ancient
import org.dembeyo.shared.resources.menu_character
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import ui.MediumBold
import ui.SmallBold
import ui.composable.CustomButton
import ui.composable.fadingEdge
import ui.darkBlue
import ui.darkPrimary
import ui.player.edit.EditCharacterScreen
import ui.secondary

class CharacterListScreen() : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()
        val viewModel: CharacterViewModel = koinInject()
        val uiState: CharacterListUiState by viewModel.uiState.collectAsState()
        Column(Modifier.fillMaxSize().padding(horizontal = 8.dp)) {
            Text(
                stringResource(Res.string.menu_character),
                modifier = Modifier.fillMaxWidth(),
                fontSize = 40.sp,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(Res.font.ancient)),
                color = darkPrimary
            )

            Divider(
                modifier = Modifier.padding(vertical = 4.dp),
                color = darkPrimary,
                thickness = 3.dp
            )

            CustomButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    navigator.push(EditCharacterScreen())
                }
            ) {
                Text("Add Character")
            }

            Divider(
                modifier = Modifier.padding(vertical = 4.dp),
                color = darkPrimary,
                thickness = 3.dp
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .fadingEdge()
            ) {
                items(uiState.characters) { item ->
                    CharacterItem(item)
                }
            }
        }

    }

    @Composable
    fun CharacterItem(character: Character) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = darkBlue,
            modifier = Modifier.fillMaxWidth()
        ) {
            ConstraintLayout(Modifier.fillMaxSize().padding(10.dp)) {
                val (profilePicture, characterName, level, playerName) = createRefs()

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

            }

        }
    }

}