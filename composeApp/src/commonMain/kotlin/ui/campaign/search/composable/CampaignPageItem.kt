package ui.campaign.search.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.delete_button
import org.dembeyo.shared.resources.edit_button
import org.dembeyo.shared.resources.round_frame
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.campaign.search.CampaignItem
import ui.composable.CustomButton
import ui.composable.darkBlue
import ui.composable.lightGray
import ui.composable.primaryDark
import ui.composable.primaryLight
import ui.composable.propertyText
import ui.composable.roundCornerShape
import ui.composable.screenTitle
import ui.composable.secondary

@Composable
fun CampaignPageItem(
    campaign: CampaignItem,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {

    val brush = Brush.linearGradient(listOf(darkBlue, primaryDark))
    Column(
        modifier = Modifier.fillMaxHeight()
            .clip(roundCornerShape)
            .background(brush)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(Modifier.weight(.4f)) {
            Image(
                painter = painterResource(Res.drawable.round_frame),
                contentDescription = "round frame",
                modifier = Modifier.align(Alignment.Center)
                    .size(300.dp)
                    .aspectRatio(1f)
                    .alpha(0.3f),
                colorFilter = ColorFilter.tint(lightGray)
            )

            Text(
                text = campaign.title,
                modifier = Modifier.align(Alignment.Center),
                style = screenTitle(secondary),
            )
        }

        LazyColumn(
            modifier = Modifier.weight(.4f)
        ) {
            item {
                Text(
                    text = campaign.description,
                    style = propertyText,
                    color = secondary
                )
            }
        }

        CustomButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onEditClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = darkBlue,
                contentColor = secondary
            )
        ) {
            Text(text = stringResource(Res.string.edit_button))
        }

        CustomButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onDeleteClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = darkBlue,
                contentColor = primaryLight
            )
        ) {
            Text(text = stringResource(Res.string.delete_button))
        }

    }
}