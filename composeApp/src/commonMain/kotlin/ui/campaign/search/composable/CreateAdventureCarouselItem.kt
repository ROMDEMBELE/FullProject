package ui.campaign.search.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.no_campaign
import org.jetbrains.compose.resources.stringResource
import ui.composable.MediumBoldSecondary
import ui.composable.darkBlue
import ui.composable.primaryDark
import ui.composable.roundCornerShape
import ui.composable.secondary

@Composable
fun CreateAdventureCarouselItem(onClick: () -> Unit) {

    val brush = Brush.linearGradient(listOf(darkBlue, primaryDark))
    Column(
        modifier = Modifier.fillMaxHeight()
            .clip(roundCornerShape)
            .background(brush)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(Res.string.no_campaign),
            style = MediumBoldSecondary
        )

        Spacer(Modifier.height(8.dp))

        IconButton(onClick = onClick) {
            Icon(
                Icons.Filled.AddCircle,
                modifier = Modifier.size(100.dp),
                contentDescription = "create campaign",
                tint = secondary
            )
        }
    }
}