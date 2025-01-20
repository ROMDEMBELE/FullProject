package ui.feat.search.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import org.dembeyo.shared.resources.Res
import org.dembeyo.shared.resources.feat_prerequisites
import org.dembeyo.shared.resources.magic_trick
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.composable.TaperedRule
import ui.composable.bounceClick
import ui.composable.darkBlue
import ui.composable.item
import ui.composable.lightBlue
import ui.composable.propertyText
import ui.composable.propertyTitle
import ui.composable.roundCornerShape
import ui.composable.secondary
import ui.composable.secondaryDark
import ui.feat.search.SearchFeatItem

@Composable
fun FeatItem(
    featItem: SearchFeatItem,
    expanded: Boolean,
    onClick: (isExpanded: Boolean) -> Unit
) {

    val color by animateColorAsState(
        targetValue = if (expanded) secondary else darkBlue,
        label = "background"
    )

    val textColor by animateColorAsState(
        targetValue = if (expanded) darkBlue else secondary,
        label = "text"
    )

    val brush = Brush.linearGradient(listOf(color, lightBlue))

    Column {
        Button(
            shape = roundCornerShape,
            border = BorderStroke(2.dp, darkBlue),
            contentPadding = PaddingValues(),
            modifier = Modifier.padding(4.dp).fillMaxWidth().height(66.dp).bounceClick(),
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            onClick = { onClick(expanded) }
        ) {
            Box(Modifier.background(brush)) {
                Image(
                    painter = painterResource(Res.drawable.magic_trick),
                    contentDescription = featItem.name,
                    colorFilter = ColorFilter.tint(color),
                    modifier = Modifier.fillMaxHeight()
                        .rotate(-20f)
                        .scale(1.5f)
                        .align(Alignment.Center)
                        .alpha(.5f)
                )
                Text(
                    text = featItem.name,
                    style = item,
                    color = textColor,
                    modifier = Modifier.padding(8.dp)
                        .fillMaxWidth()
                        .align(Alignment.Center)
                )
            }
        }
        AnimatedVisibility(expanded) {
            Surface(
                Modifier.fillMaxWidth().padding(4.dp),
                shape = roundCornerShape,
                color = secondaryDark
            ) {
                Column(Modifier.fillMaxWidth().padding(12.dp)) {
                    if (featItem.hasPrerequisites) {
                        Row(Modifier.fillMaxWidth()) {
                            Text(
                                stringResource(Res.string.feat_prerequisites),
                                style = propertyTitle,
                            )

                            Spacer(Modifier.width(8.dp))

                            Text(
                                featItem.prerequisites.toString(),
                                style = propertyText,
                            )
                        }
                        TaperedRule(Modifier.padding(vertical = 8.dp))
                    }

                    featItem.benefits.joinToString("").split(". ").forEach { description ->
                        Text(
                            text = description.replace("**", ""),
                            modifier = Modifier.padding(vertical = 4.dp),
                            style = propertyText,
                        )
                    }
                }
            }
        }
    }
}