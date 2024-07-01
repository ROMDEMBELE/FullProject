package ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.model.DamageType
import org.jetbrains.compose.resources.painterResource

@Composable
fun DamageType.generateIcon(dice: String, modifier: Modifier= Modifier) {
    Row(
        modifier.clip(roundCornerShape).background(color)
            .padding(vertical = 4.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = dice,
            fontWeight = FontWeight.Bold,
            color = darkBlue,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
        )
        Image(
            painterResource(icon),
            null,
            colorFilter = ColorFilter.tint(darkBlue),
            modifier = Modifier.size(26.dp).padding(horizontal = 4.dp)
        )
        Text(
            text = index,
            fontWeight = FontWeight.Bold,
            color = darkBlue,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
        )
    }

}