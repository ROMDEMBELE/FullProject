package ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.model.DamageType
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.color
import ui.iconRes
import ui.stringRes

@Composable
fun DamageCardItem(damageType: DamageType, modifier: Modifier = Modifier) {
    Row(
        modifier.clip(RoundedCornerShape(8.dp)).height(35.dp)
            .background(damageType.color())
            .padding(4.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(damageType.iconRes()),
            contentDescription = damageType.name,
            colorFilter = ColorFilter.tint(darkBlue),
            modifier = Modifier.size(15.dp).padding()
        )

        Spacer(Modifier.width(4.dp))

        Text(
            text = stringResource(damageType.stringRes()),
            fontWeight = FontWeight.Bold,
            color = darkBlue,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
        )
    }

}