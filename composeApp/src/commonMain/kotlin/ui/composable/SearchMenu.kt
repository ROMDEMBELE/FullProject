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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.sp

@Composable
fun SearchMenu(
    searchTextPlaceholder: String,
    searchTextFieldValue: TextFieldValue,
    onTextChange: (TextFieldValue) -> Unit,
    favoriteCounter: Int = 0,
    favoriteEnabled: Boolean,
    onFavoritesClick: (() -> Unit)? = null,
    filterContent: (@Composable () -> Unit)? = null,
) {
    // Search Bar
    var filterExpended by remember { mutableStateOf(false) }

    Column(Modifier.padding(vertical = 8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        ) {
            if (filterContent != null) {
                IconButton(
                    modifier = Modifier.then(Modifier.size(30.dp).aspectRatio(1f)),
                    enabled = !favoriteEnabled,
                    onClick = { filterExpended = !filterExpended }) {
                    Crossfade(filterExpended && !favoriteEnabled) { extended ->
                        if (extended) {
                            Icon(
                                Icons.Filled.KeyboardArrowUp, null,
                                tint = primaryDark,
                            )
                        } else {
                            Icon(
                                Icons.Filled.KeyboardArrowDown, null,
                                tint = if (favoriteEnabled) lightGray else primaryDark,
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

            if (onFavoritesClick != null) {
                IconButton(onClick = onFavoritesClick) {
                    Crossfade(favoriteEnabled) { favorite ->
                        if (favorite) {
                            Icon(
                                modifier = Modifier.then(Modifier.size(30.dp).aspectRatio(1f)),
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "search",
                                tint = primaryDark,
                            )
                        } else {
                            BadgedBox(
                                badge = {
                                    if (favoriteCounter > 0)
                                        Badge(
                                            modifier = Modifier.size(14.dp),
                                            containerColor = primary,
                                            contentColor = Color.White
                                        ) {
                                            Text("$favoriteCounter", fontSize = 8.sp)
                                        }
                                }) {
                                Icon(
                                    modifier = Modifier.then(Modifier.size(30.dp).aspectRatio(1f)),
                                    imageVector = Icons.Filled.Star,
                                    contentDescription = "favorite",
                                    tint = primaryDark,
                                )
                            }
                        }
                    }
                }
            }
        }
        AnimatedVisibility(filterExpended && !favoriteEnabled) {
            if (filterContent != null) {
                filterContent()
            }
        }
    }
}