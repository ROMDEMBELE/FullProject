package ui.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Badge
import androidx.compose.material.BadgedBox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
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
import ui.darkBlue
import ui.darkPrimary
import ui.lightGray
import ui.primary

@Composable
fun SearchMenu(
    searchTextPlaceholder: String,
    searchTextFieldValue: TextFieldValue,
    onTextChange: (TextFieldValue) -> Unit,
    favoriteCounter: Int = 0,
    onFavoritesClick: (enabled: Boolean) -> Unit,
    filterCounter: Int = 0,
    filterContent: @Composable () -> Unit
) {
    // Search Bar
    var filterExpended by remember { mutableStateOf(false) }
    var favoritesEnabled by remember { mutableStateOf(false) }

    Column(Modifier.padding(8.dp)) {

        Row(verticalAlignment = Alignment.CenterVertically) {

            BadgedBox(badge = {
                if (filterCounter > 0)
                    Badge(
                        backgroundColor = primary,
                        contentColor = Color.White
                    ) { Text("$filterCounter") }
            }) {
                IconButton({ filterExpended = !filterExpended }) {
                    Crossfade(filterExpended) { extended ->
                        if (extended) {
                            Icon(
                                Icons.Filled.KeyboardArrowUp, null,
                                tint = darkPrimary,
                                modifier = Modifier.padding(10.dp)
                            )
                        } else {
                            Icon(
                                Icons.Filled.KeyboardArrowDown, null,
                                tint = darkPrimary,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }
            }

            TextField(
                shape = RoundedCornerShape(30.dp),
                value = searchTextFieldValue,
                label = { Text(searchTextPlaceholder) },
                leadingIcon = { Icon(Icons.Filled.Search, null) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = lightGray,
                    trailingIconColor = darkPrimary,
                    leadingIconColor = darkPrimary,
                    textColor = darkBlue,
                    cursorColor = darkPrimary,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    focusedLabelColor = darkPrimary,
                    unfocusedLabelColor = darkPrimary,
                    placeholderColor = Color.Transparent
                ),
                onValueChange = onTextChange,
                modifier = Modifier.weight(1f).padding(start = 8.dp, end = 8.dp)
            )

            IconButton({
                favoritesEnabled = !favoritesEnabled
                onFavoritesClick(favoritesEnabled)
            }) {
                Crossfade(favoritesEnabled) { favorite ->
                    if (favorite) {
                        Icon(
                            Icons.Filled.Menu, null,
                            tint = darkPrimary,
                            modifier = Modifier.padding(10.dp)
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
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }
            }
        }
        AnimatedVisibility(filterExpended) {
            filterContent()
        }
    }
}