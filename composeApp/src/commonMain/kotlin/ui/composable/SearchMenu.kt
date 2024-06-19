package ui.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Badge
import androidx.compose.material.BadgedBox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import ui.darkPrimary
import ui.primary

@Composable
fun SearchMenu(
    searchTextPlaceholder: String,
    searchTextFieldValue: TextFieldValue,
    onTextChange: (TextFieldValue) -> Unit,
    favoriteCounter: Int = 0,
    favoriteEnabled: Boolean,
    onFavoritesClick: () -> Unit,
    filterCounter: Int = 0,
    filterContent: @Composable () -> Unit,
) {
    // Search Bar
    var filterExpended by remember { mutableStateOf(false) }

    Column(Modifier.padding(vertical = 8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        ) {
            BadgedBox(
                badge = {
                    if (filterCounter > 0)
                        Badge(
                            backgroundColor = primary,
                            contentColor = Color.White,
                        ) { Text("$filterCounter") }
                }
            ) {
                IconButton(
                    modifier = Modifier.then(Modifier.size(30.dp).aspectRatio(1f)),
                    enabled = !favoriteEnabled,
                    onClick = { filterExpended = !filterExpended }) {
                    Crossfade(filterExpended && !favoriteEnabled) { extended ->
                        if (extended) {
                            Icon(
                                Icons.Filled.KeyboardArrowUp, null,
                                tint = darkPrimary,
                            )
                        } else {
                            Icon(
                                Icons.Filled.KeyboardArrowDown, null,
                                tint = darkPrimary,
                            )
                        }
                    }
                }
            }
            CustomTextField(
                modifier = Modifier.padding(horizontal = 12.dp).weight(1f),
                textFieldValue = searchTextFieldValue,
                enabled = !favoriteEnabled,
                onTextChange = onTextChange,
                placeholder = searchTextPlaceholder,
                leadingIcon = { Icon(Icons.Filled.Search, null) },
            )
            IconButton(
                modifier = Modifier.then(Modifier.size(30.dp).aspectRatio(1f)),
                onClick = onFavoritesClick
            ) {
                Crossfade(favoriteEnabled) { favorite ->
                    if (favorite) {
                        Icon(
                            Icons.Filled.Menu, null,
                            tint = darkPrimary,
                        )
                    } else {
                        BadgedBox(badge = {
                            if (favoriteCounter > 0)
                                Badge(
                                    backgroundColor = primary,
                                    contentColor = Color.White
                                ) { Text("$favoriteCounter") }
                        }) {
                            Icon(
                                Icons.Filled.Star, null,
                                tint = darkPrimary,
                            )
                        }
                    }
                }
            }
        }
        AnimatedVisibility(filterExpended && !favoriteEnabled) {
            filterContent()
        }
    }
}