package ui.player

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import domain.model.character.Character
import org.koin.compose.koinInject

class CharacterListScreen() : Screen {

    @Composable
    override fun Content() {
        val scope = rememberCoroutineScope()
        val viewModel: CharacterViewModel = koinInject()
        val uiState: CharacterListUiState by viewModel.uiState.collectAsState()
        LazyColumn {
            items(uiState.characters) { item ->

            }

        }
    }

    @Composable
    fun CharacterItem(character: Character) {
        Surface(shape = RoundedCornerShape(20.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {



            }

        }
    }

}