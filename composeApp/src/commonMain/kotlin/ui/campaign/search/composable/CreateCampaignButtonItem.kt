package ui.campaign.search.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.no_campaign
import org.jetbrains.compose.resources.stringResource
import ui.composable.MediumBoldSecondary
import ui.composable.darkBlue
import ui.composable.roundCornerShape
import ui.composable.secondary

@Composable
fun CreateCampaignButtonItem(onClick: () -> Unit) {
    Button(
        shape = roundCornerShape,
        modifier = Modifier.fillMaxSize(),
        colors = ButtonDefaults.buttonColors(
            containerColor = darkBlue,
            contentColor = secondary
        ),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(Res.string.no_campaign),
                style = MediumBoldSecondary
            )

            Spacer(Modifier.height(16.dp))

            Icon(
                Icons.Filled.AddCircle,
                modifier = Modifier.size(50.dp),
                contentDescription = "create campaign",
                tint = secondary
            )
        }
    }
}