package ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

val primary = Color(205, 97, 85)
val lightPrimary = Color(254, 25, 88)
val darkPrimary = Color(123, 36, 28)
val darkGray = Color(93, 109, 126)
val darkBlue = Color(46, 64, 83)
val lightBlue = Color(105, 127, 170)
val lightGray = Color(174, 182, 191)

val mediumBoldWhite = TextStyle(
    color = Color.White,
    fontSize = 16.sp,
    fontWeight = FontWeight.Bold,
    textAlign = TextAlign.Center,
)

val smallBoldWhite = TextStyle(
    color = Color.White,
    fontSize = 12.sp,
    fontWeight = FontWeight.Bold,
    textAlign = TextAlign.Center,
)

val clip = TextStyle(
    fontWeight = FontWeight.Bold,
    textAlign = TextAlign.Center,
    fontSize = 14.sp
)

val smallBold = TextStyle(
    fontSize = 14.sp,
    fontWeight = FontWeight.Bold
)

val item = TextStyle(
    textAlign = TextAlign.Center,
    fontWeight = FontWeight.SemiBold,
    fontFamily = FontFamily.Monospace,
    color = Color.White,
    fontSize = 18.sp,
)