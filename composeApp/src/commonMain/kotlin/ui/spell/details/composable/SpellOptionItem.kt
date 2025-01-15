package ui.spell.details.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.model.DamageType
import domain.model.Level
import ui.color
import ui.composable.DamageCardItem
import ui.composable.SmallBoldDarkBlue
import ui.composable.darkBlue
import ui.composable.secondary

@Composable
fun SpellOptionItem(
    level: Level,
    dice: String?,
    duration: String?,
    targetCount: Int?,
    type: List<DamageType> = emptyList()
) {
    val damageBrush = Brush.linearGradient(listOf(darkBlue, darkBlue, level.color()))
    Row(
        Modifier.height(70.dp).fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(damageBrush)
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Lv${level.level}",
            style = SmallBoldDarkBlue,
            modifier = Modifier.clip(RoundedCornerShape(8.dp))
                .background(level.color())
                .padding(8.dp)

        )

        Spacer(Modifier.width(4.dp))

        if (duration != null) {
            Text(
                text = duration,
                style = SmallBoldDarkBlue.copy(color = darkBlue, fontSize = 14.sp),
                modifier = Modifier.clip(RoundedCornerShape(8.dp))
                    .background(secondary)
                    .padding(8.dp)
            )
            Spacer(Modifier.width(4.dp))
        }

        if (targetCount != null) {
            Text(
                text = "Target : $targetCount",
                style = SmallBoldDarkBlue.copy(color = darkBlue, fontSize = 14.sp),
                modifier = Modifier.clip(RoundedCornerShape(8.dp))
                    .background(secondary)
                    .padding(8.dp)
            )
            Spacer(Modifier.width(4.dp))
        }

        if (dice != null) {
            Text(
                text = dice,
                style = SmallBoldDarkBlue.copy(color = darkBlue, fontSize = 14.sp),
                modifier = Modifier.clip(RoundedCornerShape(8.dp))
                    .background(secondary)
                    .padding(8.dp)
            )
            Spacer(Modifier.width(4.dp))
        }

        for (damageType in type) {
            DamageCardItem(damageType)
        }
    }
}