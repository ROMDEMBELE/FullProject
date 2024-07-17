package ui.magicItem.list

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.model.magicItem.MagicItem
import domain.model.magicItem.Rarity
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.error_dialog_title
import org.dembeyo.shared.resources.magic_item
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import ui.composable.CustomAnimatedPlaceHolder
import ui.composable.CustomErrorDialog
import ui.composable.CustomLazyHeaderList
import ui.composable.MediumBold
import ui.composable.SearchMenu
import ui.composable.TaperedRule
import ui.composable.bounceClick
import ui.composable.darkBlue
import ui.composable.darkGray
import ui.composable.item
import ui.composable.primary
import ui.composable.roundCornerShape
import ui.composable.secondary
import ui.magicItem.details.MagicItemDetailsScreen


class MagicItemListScreen() : Screen {

    override val key: ScreenKey
        get() = uniqueScreenKey

    @Composable
    override fun Content() {
        val viewModel: MagicItemListViewModel = koinInject()
        val uiState by viewModel.uiState.collectAsState()
        var favoriteEnabled by rememberSaveable { mutableStateOf(false) }
        AnimatedVisibility(uiState.hasError) {
            CustomErrorDialog(
                stringResource(Res.string.error_dialog_title),
                uiState.error.orEmpty()
            ) {
                viewModel.acknowledgeError()
            }
        }

        CustomAnimatedPlaceHolder()

        AnimatedVisibility(uiState.isReady, enter = fadeIn()) {
            Column(
                Modifier.fillMaxSize().background(secondary),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchMenu(
                    searchTextPlaceholder = "Search by name",
                    searchTextFieldValue = uiState.textField,
                    onTextChange = { viewModel.filterByText(it) },
                    favoriteCounter = uiState.favoriteCounter,
                    favoriteEnabled = favoriteEnabled,
                    onFavoritesClick = { favoriteEnabled = !favoriteEnabled },
                    filterContent = {
                        LazyVerticalGrid(
                            modifier = Modifier.padding(8.dp),
                            columns = GridCells.Fixed(2),
                        ) {
                            items(Rarity.entries) { rarity ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.height(30.dp)
                                ) {
                                    Checkbox(
                                        colors = CheckboxDefaults.colors(darkBlue),
                                        checked = uiState.rarityFilter.contains(rarity),
                                        onCheckedChange = { checked ->
                                            if (checked) {
                                                viewModel.addRarityToFilter(rarity)
                                            } else {
                                                viewModel.removeRarityFromFilter(rarity)
                                            }
                                        })
                                    Text(
                                        text = rarity.text,
                                        modifier = Modifier.weight(1f),
                                        style = MediumBold.copy(
                                            color = darkBlue,
                                            textAlign = TextAlign.Start
                                        )
                                    )
                                }
                            }
                        }
                    },
                )

                TaperedRule()

                AnimatedContent(favoriteEnabled) { favorite ->
                    if (favorite) {
                        ListOfMagicItem(uiState.favoriteItemsByRarity, viewModel)
                    } else {
                        ListOfMagicItem(uiState.filteredMagicItemsByRarity, viewModel)
                    }
                }
            }
        }
    }

    @Composable
    fun ListOfMagicItem(
        magicItemsByRarity: Map<Rarity, List<MagicItem>>,
        viewModel: MagicItemListViewModel
    ) {
        val navigator = LocalNavigator.currentOrThrow
        CustomLazyHeaderList(
            mapOfValue = magicItemsByRarity,
            stickyMode = false,
            header = { rarity ->
                Text(
                    text = rarity.text,
                    modifier = Modifier.clip(CutCornerShape(8.dp))
                        .background(rarity.color)
                        .border(2.dp, darkBlue, CutCornerShape(8.dp))
                        .fillMaxWidth()
                        .padding(8.dp),
                    style = MediumBold.copy(color = secondary)
                )
            },
            item = { magicItem ->
                MagicItem(
                    magicItem = magicItem,
                    onClick = {
                        navigator.push(MagicItemDetailsScreen(magicItem.index))
                    },
                    onFavoriteClick = {
                        if (magicItem.isFavorite) {
                            viewModel.removeFromFavorite(magicItem)
                        } else {
                            viewModel.addToFavorite(magicItem)
                        }
                    })
            },
        )

    }

    @Composable
    fun MagicItem(magicItem: MagicItem, onClick: () -> Unit, onFavoriteClick: () -> Unit) {
        Button(
            shape = roundCornerShape,
            border = BorderStroke(2.dp, primary),
            contentPadding = PaddingValues(),
            modifier = Modifier.padding(4.dp).fillMaxWidth().height(66.dp).bounceClick(),
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            onClick = onClick
        ) {
            val brush = Brush.linearGradient(listOf(darkBlue, magicItem.rarity.color))
            Box(Modifier.background(brush)) {
                Image(
                    painterResource(Res.drawable.magic_item),
                    null,
                    colorFilter = ColorFilter.tint(primary),
                    modifier = Modifier.fillMaxHeight()
                        .rotate(-20f)
                        .scale(1.5f)
                        .align(Alignment.Center)
                        .alpha(.5f)
                )
                Text(
                    magicItem.name,
                    style = item.copy(fontSize = 18.sp),
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 50.dp)
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
                        tint = if (magicItem.isFavorite) Color.Yellow else darkGray
                    )
                }
            }
        }
    }
}

