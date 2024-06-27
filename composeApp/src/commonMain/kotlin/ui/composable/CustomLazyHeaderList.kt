package ui.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T, V> CustomLazyHeaderList(
    mapOfValue: Map<T, List<V>>,
    header: @Composable (T) -> Unit,
    item: @Composable (V) -> Unit,
    stickyMode: Boolean = false,
) {
    val listState = rememberSaveable(saver = LazyListState.Saver) { LazyListState(0, 0) }
    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .fadingEdge()
    ) {
        mapOfValue.forEach { (key, listOfValue) ->
            if (stickyMode) {
                stickyHeader(key) {
                    Column(Modifier.padding(vertical = 8.dp).alpha(0.8f)) {
                        header(key)
                    }
                }
            } else {
                item {
                    Column(Modifier.padding(vertical = 8.dp).alpha(0.9f)) {
                        header(key)
                    }
                }
            }
            items(items = listOfValue) { value ->
                item(value)
            }
        }
    }
}