package ui.monster.details.composable

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
import domain.model.monster.Action
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.monster_attack
import org.jetbrains.compose.resources.stringResource
import ui.composable.SmallBoldSecondary
import ui.composable.darkBlue
import ui.composable.darkGray
import ui.composable.propertyText
import ui.composable.secondary
import ui.stringRes

@Composable
fun ActionItem(action: Action) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp)
            .clip(RoundedCornerShape(8.dp)).background(secondary),
    ) {
        Text(
            text = action.name,
            style = SmallBoldSecondary,
            modifier = Modifier.fillMaxWidth().background(darkBlue).padding(4.dp)
        )

        Text(
            text = action.desc.capitalize(Locale.current),
            modifier = Modifier.padding(8.dp),
            style = propertyText.copy(textAlign = TextAlign.Center)
        )

        action.attacks.forEach { attack ->
            Text(
                text = stringResource(
                    Res.string.monster_attack,
                    attack.attackBonus,
                    attack.damageDice,
                    stringResource(attack.damageType.stringRes())
                ),
                style = SmallBoldSecondary,
                modifier = Modifier.fillMaxWidth().background(darkGray).padding(4.dp)
            )
        }
    }
}