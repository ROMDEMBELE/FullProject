package ui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val primary = Color(205, 97, 85)
val darkPrimary = Color(123, 36, 28)
val darkGray = Color(93, 109, 126)
val darkBlue = Color(28, 43, 98)
val lightBlue = Color(95, 99, 175)
val lightGray = Color(174, 182, 191)
val secondary = Color(255, 248, 219)
val orange = Color(0xFFE69A28)

val roundCornerShape = RoundedCornerShape(18.dp)

val BigBold = TextStyle(
    color = darkPrimary,
    fontSize = 18.sp,
    fontWeight = FontWeight.Bold,
    fontStyle = FontStyle.Italic,
    textAlign = TextAlign.Center,
)

val MediumBold = TextStyle(
    color = secondary,
    fontSize = 16.sp,
    fontWeight = FontWeight.Bold,
    textAlign = TextAlign.Center,
)

val SmallBold = MediumBold.copy(
    fontSize = 14.sp,
)

val monsterTitle = TextStyle(
    fontSize = 23.sp,
    fontWeight = FontWeight.Bold,
    color = darkPrimary,
    fontFamily = FontFamily.Serif,
)

val monsterSubTitle = TextStyle(
    fontSize = 12.sp,
    fontStyle = FontStyle.Italic,
    color = Color.Black,
)

val monsterPropertyText = TextStyle(
    color = darkPrimary,
    fontSize = 13.5.sp,
    lineHeight = 16.sp
)

val monsterPropertyTitle = monsterPropertyText.copy(
    fontWeight = FontWeight.Bold
)

val spellDetailsText = TextStyle(
    color = secondary,
    fontSize = 18.sp,
    fontWeight = FontWeight.Bold,
    textAlign = TextAlign.Center,
)

val clip = TextStyle(
    fontWeight = FontWeight.Bold,
    textAlign = TextAlign.Center,
    fontSize = 14.sp
)

val item = TextStyle(
    textAlign = TextAlign.Center,
    fontWeight = FontWeight.SemiBold,
    color = secondary,
    fontSize = 20.sp,
)

