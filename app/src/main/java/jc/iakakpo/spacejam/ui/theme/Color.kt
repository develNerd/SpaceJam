package jc.iakakpo.spacejam.ui.theme

import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFF3DDC84)
val Purple500 = Color(0xFF37BF6E)
val Purple700 = Color(0xFF688172)
val Teal200 = Color(0xFF03DAC5)

val colorPrimary = Color(0xFF083042)
val spaceBlueLight = Color(0xFF4285F4)
val spaceBlueDark = Color(0xFF3870B2)
val spaceGreenLight = Color(0xFF3DDC84)
val spaceGreenDark = Color(0xFF37BF6E)
val spaceGreenTransluscent = Color(0xFFF9E8E8)
val spacePrimaryDark = Color(0xFF041619)
val spaceLightGreen = Color(0xFFF9E8E8)
val spaceGreenLightTrans = Color(0x2C3DDC84)


fun backGroundColor(isDark:Boolean = false) = if (!isDark) Color(0xFFFFFFFF) else colorPrimary
fun textColor(isDark:Boolean = false) = if (!isDark) colorPrimary else Color(0xFFD1C5C5)
