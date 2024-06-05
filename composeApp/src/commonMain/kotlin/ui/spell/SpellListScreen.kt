package ui.spell

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Badge
import androidx.compose.material.BadgedBox
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.Spell
import fullproject.composeapp.generated.resources.Res
import fullproject.composeapp.generated.resources.magic
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import ui.darkBlue
import ui.item
import ui.lightBlue
import ui.lightGray
import ui.mediumBoldWhite
import ui.primary
import ui.smallBold


class SpellListScreen : Screen {

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val viewModel: SpellScreenViewModel = koinInject()
        val uiState = viewModel.uiState.collectAsState()
        val listState = rememberSaveable(saver = LazyListState.Saver) {
            LazyListState(0, 0)
        }
        val scope = rememberCoroutineScope()
        val navigator = LocalNavigator.currentOrThrow
        Column {
            // Search Bar
            val settingsExpended = remember { mutableStateOf(false) }

            Column(Modifier.padding(8.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton({ settingsExpended.value = !settingsExpended.value }) {
                        // Display the number of enabled Filter
                        BadgedBox(badge = {
                            if (uiState.value.filterCounter > 0)
                                Badge(
                                    backgroundColor = primary,
                                    contentColor = Color.White
                                ) { Text("$uiState.value.filterCounter") }
                        }) {
                            Crossfade(settingsExpended.value) { extended ->
                                if (extended) {
                                    Icon(
                                        Icons.Filled.KeyboardArrowUp, null,
                                        tint = Color.White,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                } else {
                                    Icon(
                                        Icons.Filled.KeyboardArrowDown, null,
                                        tint = Color.White,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                }
                            }
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        TextField(
                            shape = RoundedCornerShape(20.dp),
                            value = uiState.value.textField,
                            label = { Text("Chercher par Nom") },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                backgroundColor = lightGray,
                                cursorColor = Color.White,
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent,
                                textColor = Color.White,
                                focusedLabelColor = Color.White,

                                ),
                            trailingIcon = { Icon(Icons.Filled.Search, null, tint = Color.White) },
                            onValueChange = { //viewModel.updateTextField(it)
                            },
                            modifier = Modifier.weight(1f).padding(start = 8.dp, end = 8.dp)
                        )
                    }
                }
                AnimatedVisibility(settingsExpended.value) {
                    LazyVerticalGrid(
                        modifier = Modifier.padding(8.dp),
                        columns = GridCells.Fixed(2),
                    ) {
                        items(uiState.value.filterByLevel.entries.toList()) { (level, checked) ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.height(30.dp)
                            ) {
                                Checkbox(
                                    colors = CheckboxDefaults.colors(primary),
                                    checked = checked,
                                    onCheckedChange = {
                                        viewModel.filterLevel(level, it)
                                    })
                                Text(
                                    "Level ${level.level}",
                                    Modifier.weight(1f),
                                    style = smallBold
                                )
                            }
                        }
                        items(uiState.value.filterByMagicSchool.entries.toList()) { (school, checked) ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.height(30.dp)
                            ) {
                                Checkbox(
                                    colors = CheckboxDefaults.colors(primary),
                                    checked = checked,
                                    onCheckedChange = {
                                        viewModel.filterMagicSchool(school, it)
                                    })
                                Text(
                                    school.displayName,
                                    Modifier.weight(1f),
                                    style = smallBold
                                )

                            }

                        }
                    }
                }
            }

            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
            ) {
                uiState.value.spellsByLevel.forEach { (level, spells) ->
                    stickyHeader(level) {
                        Column(Modifier.padding(vertical = 8.dp).alpha(0.7f)) {
                            Text(
                                text = "Level ${level.level}",
                                modifier = Modifier.clip(CutCornerShape(8.dp))
                                    .background(level.color)
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                style = mediumBoldWhite.copy(color = darkBlue)
                            )
                        }
                    }
                    items(items = spells) {
                        SpellItem(
                            spell = it,
                            onClick = {
                                scope.launch {
                                    viewModel.getSpell(it.index)?.let { completeSpell ->
                                        navigator.push(SpellDetailsScreen(completeSpell))
                                    }
                                }
                            },
                            onFavoriteClick = {
                                scope.launch {
                                    viewModel.updateFavorite(it)
                                }
                            })
                    }
                }
            }
        }
    }

    @Composable
    fun SpellItem(spell: Spell, onClick: () -> Unit, onFavoriteClick: () -> Unit) {
        Button(
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(2.dp, primary),
            modifier = Modifier.padding(4.dp).fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(darkBlue),
            onClick = onClick
        ) {
            Box {
                Image(
                    painterResource(Res.drawable.magic),
                    null,
                    colorFilter = ColorFilter.tint(primary),
                    modifier = Modifier.align(Alignment.Center).height(50.dp).alpha(.7f)
                )
                Text(
                    spell.name,
                    style = item,
                    modifier = Modifier.padding(8.dp)
                        .fillMaxWidth()
                        .align(Alignment.Center)
                )
                IconButton(
                    onClick = onFavoriteClick,
                    modifier = Modifier.align(Alignment.CenterEnd).padding(10.dp)
                ) {
                    Icon(
                        Icons.Filled.Star,
                        null,
                        tint = if (spell.isFavorite) Color.Yellow else lightBlue
                    )
                }
            }
        }
    }
}

