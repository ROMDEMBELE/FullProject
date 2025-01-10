package ui.monster.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import ui.composable.propertyText
import ui.composable.propertyTitle
import ui.composable.secondary

@Composable
fun PropertyLine(title: StringResource, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp)
            .clip(RoundedCornerShape(8.dp)).background(secondary),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(title),
            style = propertyTitle,
            modifier = Modifier.padding(4.dp)
        )
        Text(
            text = value.capitalize(Locale.current),
            modifier = Modifier.padding(4.dp),
            textAlign = TextAlign.End,
            style = propertyText,
        )
    }
}