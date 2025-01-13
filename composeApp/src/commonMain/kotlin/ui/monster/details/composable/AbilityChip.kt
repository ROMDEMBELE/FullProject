package ui.monster.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import domain.model.Ability.Companion.getAbilityBonus
import ui.composable.MediumBoldSecondary
import ui.composable.SmallBoldSecondary
import ui.composable.darkPrimary
import ui.getAbilityBonusColor

@Composable
fun RowScope.AbilityChip(abilityName: String, abilityValue: Int) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 1.dp).weight(1f).clip(RoundedCornerShape(4.dp))
            .background(darkPrimary)
    ) {
        Text(
            text = "$abilityName ($abilityValue)",
            style = SmallBoldSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(2.dp)
        )
        val bonus = abilityValue.getAbilityBonus()
        val signedBonus = if (bonus > 0) "+$bonus" else "$bonus"
        Text(
            text = signedBonus,
            modifier = Modifier.fillMaxWidth().background(bonus.getAbilityBonusColor())
                .padding(2.dp),
            textAlign = TextAlign.Center,
            style = MediumBoldSecondary.copy(color = darkPrimary)
        )
    }
}