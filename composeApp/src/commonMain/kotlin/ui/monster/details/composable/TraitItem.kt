package ui.monster.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import domain.model.monster.Trait
import ui.composable.SmallBoldSecondary
import ui.composable.darkPrimary
import ui.composable.propertyText
import ui.composable.secondary

@Composable
fun TraitItem(trait: Trait) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp)
            .clip(RoundedCornerShape(8.dp)).background(secondary),
    ) {
        Text(
            text = trait.name,
            style = SmallBoldSecondary.copy(color = secondary),
            modifier = Modifier.fillMaxWidth().background(darkPrimary).padding(4.dp)
        )
        Text(
            text = trait.desc.capitalize(Locale.current),
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            style = propertyText.copy(textAlign = TextAlign.Center)
        )
    }
}