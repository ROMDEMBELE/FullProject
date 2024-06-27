package ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.model.DamageType
import org.jetbrains.compose.resources.painterResource

@Composable
fun DamageType.generateView(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.clip(CircleShape).background(darkBlue)
            .padding(vertical = 8.dp, horizontal = 12.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painterResource(icon),
            null,
            colorFilter = ColorFilter.tint(color),
            modifier = Modifier.size(20.dp).aspectRatio(1f)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = index.capitalize(Locale.current),
            color = secondary,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }
}